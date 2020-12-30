package pl.devzyra.librotecareactivestack.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import pl.devzyra.librotecareactivestack.entities.Author;
import pl.devzyra.librotecareactivestack.entities.BookDocument;
import pl.devzyra.librotecareactivestack.entities.UserDocument;
import pl.devzyra.librotecareactivestack.repositories.BookElasticReactiveRepository;
import pl.devzyra.librotecareactivestack.repositories.UserElasticReactiveRepository;

import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class DataLoad {

    private final BookElasticReactiveRepository bookRepository;
    private final UserElasticReactiveRepository userRepository;

    public DataLoad(BookElasticReactiveRepository bookRepository, UserElasticReactiveRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {

        bookRepository.deleteAll().subscribe();
        userRepository.deleteAll().subscribe();

        bookRepository.save(BookDocument.builder().title("Witcher").authors(List.of(new Author("Andrzej Sapkowski"))).build()).block();
        bookRepository.save(BookDocument.builder().title("Switcher").authors(List.of(new Author("Sapkow Andrzej Andrzejowski"))).build()).block();

        userRepository.save(UserDocument.builder()
                .firstname("admin").lastname("admin")
                .email("admin").encryptedPassword("{bcrypt}$2a$10$X/GEwCWons1vyPEz08UaUer85yXPApzc8MuCYTghx4XqqgNuP5QFS")
                .roles(Set.of(new SimpleGrantedAuthority("ROLE_ADMIN"),new SimpleGrantedAuthority("ROLE_USER")))
                .build())
                .block();

        System.out.println("### Data Loaded ###");
        bookRepository.count().subscribe(count -> System.out.println("Book repository count: " + count));
        userRepository.count().subscribe(count -> System.out.println("User repository count: " + count));
    }
}

package pl.devzyra.librotecareactivestack.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.devzyra.librotecareactivestack.entities.Author;
import pl.devzyra.librotecareactivestack.entities.BookDocument;
import pl.devzyra.librotecareactivestack.entities.UserDocument;
import pl.devzyra.librotecareactivestack.repositories.BookElasticReactiveRepository;
import pl.devzyra.librotecareactivestack.repositories.UserElasticReactiveRepository;

import java.util.List;

@Component
public class DataLoad implements CommandLineRunner {

    private final BookElasticReactiveRepository bookRepository;
    private final UserElasticReactiveRepository userRepository;

    public DataLoad(BookElasticReactiveRepository bookRepository, UserElasticReactiveRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        bookRepository.deleteAll().subscribe();
        userRepository.deleteAll().subscribe();

        bookRepository.save(BookDocument.builder().title("Witcher").authors(List.of(new Author("Andrzej Sapkowski"))).build()).block();
        bookRepository.save(BookDocument.builder().title("Switcher").authors(List.of(new Author("Sapkow Andrzej Andrzejowski"))).build()).block();

        userRepository.save(UserDocument.builder().firstname("admin").lastname("admin").email("admin@libroteca.com").build()).block();

        System.out.println("### Data Loaded ###");
        System.out.println("Book repository count:" + bookRepository.count().block());
        System.out.println("User repository count:" + userRepository.count().block());
    }
}

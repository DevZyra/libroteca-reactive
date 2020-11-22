package pl.devzyra.librotecareactivestack.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.devzyra.librotecareactivestack.entities.BookDocument;
import pl.devzyra.librotecareactivestack.entities.UserDocument;
import pl.devzyra.librotecareactivestack.repositories.BookReactiveRepository;
import pl.devzyra.librotecareactivestack.repositories.UserReactiveRepository;

@Component
public class DataLoad implements CommandLineRunner {

    private final BookReactiveRepository bookRepository;
    private final UserReactiveRepository userRepository;

    public DataLoad(BookReactiveRepository bookRepository, UserReactiveRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        bookRepository.deleteAll().subscribe();
        userRepository.deleteAll().subscribe();

        bookRepository.save(BookDocument.builder().title("Witcher").build()).block();
        bookRepository.save(BookDocument.builder().title("Switcher").build()).block();

        userRepository.save(UserDocument.builder().firstname("admin").lastname("admin").email("admin@libroteca.com").build()).block();

        System.out.println("### Data Loaded ###");
        System.out.println("Book repository count:" + bookRepository.count().block());
        System.out.println("User repository count:" + userRepository.count().block());
    }
}

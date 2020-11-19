package pl.devzyra.librotecareactivestack.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.devzyra.librotecareactivestack.entities.BookDocument;
import pl.devzyra.librotecareactivestack.repositories.BookReactiveRepository;

@Component
public class DataLoad implements CommandLineRunner {

    private final BookReactiveRepository bookRepository;

    public DataLoad(BookReactiveRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        bookRepository.deleteAll().subscribe();

        bookRepository.save(BookDocument.builder().title("Witcher").build()).block();
        bookRepository.save(BookDocument.builder().title("Switcher").build()).block();


        System.out.println("### Data Loaded ###");
        System.out.println("Book repository count:" + bookRepository.count().block());
    }
}

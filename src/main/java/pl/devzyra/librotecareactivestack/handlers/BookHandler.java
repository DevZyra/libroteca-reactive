package pl.devzyra.librotecareactivestack.handlers;

import org.springframework.stereotype.Component;
import pl.devzyra.librotecareactivestack.repositories.BookReactiveRepository;

@Component
public class BookHandler {

 private BookReactiveRepository bookRepository;

    public BookHandler(BookReactiveRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
}

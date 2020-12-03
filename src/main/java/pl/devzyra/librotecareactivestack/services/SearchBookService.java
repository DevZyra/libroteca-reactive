package pl.devzyra.librotecareactivestack.services;

import pl.devzyra.librotecareactivestack.entities.BookDocument;
import reactor.core.publisher.Flux;

public interface SearchBookService {

    Flux<BookDocument> searchByTitle(String title);

    Flux<BookDocument> searchByAuthor(String name);

}

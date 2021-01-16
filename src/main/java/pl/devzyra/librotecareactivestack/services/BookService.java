package pl.devzyra.librotecareactivestack.services;

import pl.devzyra.librotecareactivestack.entities.BookDocument;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {

    Mono<BookDocument> getBookById(String id);

    Flux<BookDocument> getAllBooks();

    Flux<BookDocument> getBooksPaged(int page, int limit);

    Mono<BookDocument> saveBook(BookDocument bookDocument);

    Mono<BookDocument> deleteBook(String id);

    Mono<BookDocument> updateBook(String id, BookDocument bookDocument);

}

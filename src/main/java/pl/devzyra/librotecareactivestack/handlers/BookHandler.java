package pl.devzyra.librotecareactivestack.handlers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pl.devzyra.librotecareactivestack.entities.BookDocument;
import pl.devzyra.librotecareactivestack.services.BookService;
import reactor.core.publisher.Mono;

@Component
public class BookHandler {

    private final BookService bookService;

    public BookHandler(BookService bookService) {
        this.bookService = bookService;
    }

    public Mono<ServerResponse> getBook(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        return bookService.getBookById(id)
                .flatMap(result -> ServerResponse.ok().body(BodyInserters.fromValue(result)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getAllBooks(ServerRequest serverRequest) {
        return ServerResponse.ok().body(bookService.getAllBooks(), BookDocument.class);
    }


    public Mono<ServerResponse> createBook(ServerRequest serverRequest) {

        Mono<BookDocument> bookMono = serverRequest.bodyToMono(BookDocument.class);

        return bookMono.flatMap(book ->
                ServerResponse.ok().body(bookService.saveBook(book), BookDocument.class));
    }

    public Mono<ServerResponse> updateBook(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        Mono<BookDocument> bookUpdated = serverRequest.bodyToMono(BookDocument.class)
                .flatMap(
                        bookUpd -> {
                            return bookService.getBookById(id).flatMap(
                                    bookDb -> {
                                        bookDb.setTitle(bookUpd.getTitle());
                                        bookDb.setAuthors(bookUpd.getAuthors());
                                        return bookService.saveBook(bookDb);
                                    });
                        });
        return bookUpdated.flatMap(book -> ServerResponse.ok().body(BodyInserters.fromValue(book))).switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteBook(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        return bookService.deleteBook(id)
                .flatMap(book -> ServerResponse.noContent().build()).switchIfEmpty(ServerResponse.notFound().build());
    }
}

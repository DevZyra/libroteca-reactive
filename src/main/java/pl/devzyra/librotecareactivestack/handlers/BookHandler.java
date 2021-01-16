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


    public Mono<ServerResponse> getBooksPaged(ServerRequest serverRequest) {
        int page = Integer.parseInt(serverRequest.queryParam("page").orElse("0"));
        int limit = Integer.parseInt(serverRequest.queryParam("limit").orElse("25"));

        return ServerResponse.ok().body(bookService.getBooksPaged(page, limit), BookDocument.class);
    }

    public Mono<ServerResponse> createBook(ServerRequest serverRequest) {

        Mono<BookDocument> bookMono = serverRequest.bodyToMono(BookDocument.class);

        return bookMono.flatMap(book ->
                ServerResponse.ok().body(bookService.saveBook(book), BookDocument.class));
    }

    public Mono<ServerResponse> updateBook(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        return serverRequest.bodyToMono(BookDocument.class)
                .flatMap(book -> bookService.updateBook(id, book))
                .flatMap(bookDocument -> ServerResponse.accepted().bodyValue(bookDocument))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> deleteBook(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        return bookService.deleteBook(id)
                .flatMap(book -> ServerResponse.noContent().build()).switchIfEmpty(ServerResponse.notFound().build());
    }
}

package pl.devzyra.librotecareactivestack.handlers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pl.devzyra.librotecareactivestack.entities.BookDocument;
import pl.devzyra.librotecareactivestack.services.SearchBookService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class SearchBookHandler {

    private final SearchBookService searchBookService;

    public SearchBookHandler(SearchBookService searchBookService) {
        this.searchBookService = searchBookService;
    }

    public Mono<ServerResponse> searchBookByTitle(ServerRequest serverRequest) {

        Flux<BookDocument> bookDocumentFlux = searchBookService.searchByTitle(serverRequest.queryParam("title").orElse(""));

        return ServerResponse.ok().body(bookDocumentFlux, BookDocument.class);
    }

    public Mono<ServerResponse> searchBookByAuthor(ServerRequest serverRequest) {

        Flux<BookDocument> bookDocumentFlux = searchBookService.searchByAuthor(serverRequest.queryParam("author").orElse(""));

        return ServerResponse.ok().body(bookDocumentFlux, BookDocument.class);

    }
}

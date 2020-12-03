package pl.devzyra.librotecareactivestack.handlers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pl.devzyra.librotecareactivestack.entities.BookDocument;
import pl.devzyra.librotecareactivestack.services.SearchBookService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class SearchBookHandler {

    private final SearchBookService searchBookService;

    public SearchBookHandler(SearchBookService searchBookService) {
        this.searchBookService = searchBookService;
    }

    public Mono<ServerResponse> searchBookByTitle(ServerRequest serverRequest) {
        Optional<String> qtitle = serverRequest.queryParam("title");
        String str = qtitle.get();

        Flux<BookDocument> bookDocumentFlux = searchBookService.searchByTitle(str);

        return ServerResponse.ok().body(bookDocumentFlux, BookDocument.class);
    }

    public Mono<ServerResponse> searchBookByAuthor(ServerRequest serverRequest) {
        Optional<String> qauthor = serverRequest.queryParam("author");
        String s = qauthor.get();


        Flux<BookDocument> bookDocumentFlux = searchBookService.searchByAuthor(s);

        return ServerResponse.ok().body(bookDocumentFlux, BookDocument.class);

    }
}

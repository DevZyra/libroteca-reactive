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

}

package pl.devzyra.librotecareactivestack.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pl.devzyra.librotecareactivestack.handlers.BookHandler;
import pl.devzyra.librotecareactivestack.handlers.SearchBookHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RequestPredicates.queryParam;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BookRouter {

    @Bean
    RouterFunction<ServerResponse> bookRoutes(BookHandler bookHandler, SearchBookHandler searchBookHandler) {

        return route()
                .nest(path("/books"), builder -> builder
                        .GET("", queryParam("title", t -> true), searchBookHandler::searchBookByTitle)
                        .GET("", queryParam("author", t -> true), searchBookHandler::searchBookByAuthor)
                        .GET("", bookHandler::getAllBooks)
                        .POST("", bookHandler::createBook)
                ).build();
    }

    @Bean
    RouterFunction<ServerResponse> bookRoutesWithId(BookHandler bookHandler) {

        return route()
                .nest(path("/books/{id}"), builder -> builder
                        .GET("", bookHandler::getBook)
                        .PUT("", bookHandler::updateBook)
                        .DELETE("", bookHandler::deleteBook))
                .build();
    }


}

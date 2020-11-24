package pl.devzyra.librotecareactivestack.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pl.devzyra.librotecareactivestack.handlers.SearchBookHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class SearchBookRouter {

    @Bean
    RouterFunction<ServerResponse> searchBookRoutes(SearchBookHandler searchBookHandler) {

        return route()
                .nest(path("/books"), builder -> builder
                        .GET("/{title}", searchBookHandler::searchBookByTitle)
                ).build();
    }

}

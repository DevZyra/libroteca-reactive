package pl.devzyra.librotecareactivestack.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pl.devzyra.librotecareactivestack.handlers.UserHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RequestPredicates.queryParam;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UserRouter {

    @Bean
    RouterFunction<ServerResponse> userRoutes(UserHandler userHandler) {

        return route()
                .nest(path("/users"), builder -> builder
                        .GET("", queryParam("page", t -> true).and(queryParam("limit", t -> true)), userHandler::getUsersPaged)
                        .GET("", userHandler::getAllUsers)
                        .POST("", userHandler::createUser)
                ).build();
    }

    @Bean
    RouterFunction<ServerResponse> userRoutesWithId(UserHandler userHandler) {

        return route()
                .nest(path("/users/{id}"), builder -> builder
                        .GET("", userHandler::getUser)
                        .PUT("", userHandler::updateUser)
                        .DELETE("", userHandler::deleteUser)
                ).build();
    }
}

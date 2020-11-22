package pl.devzyra.librotecareactivestack.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pl.devzyra.librotecareactivestack.handlers.UserHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UserRouter {

    @Bean
    RouterFunction<ServerResponse> userRoutes(UserHandler userHandler) {

        return route()
                .nest(path("/users"), builder -> builder
                        .GET("", userHandler::getAllUsers)
                        .GET("/{id}", userHandler::getUser)
                        .POST("", userHandler::createUser)
                        .PUT("/{id}", userHandler::updateUser))
                .build();


    }

}

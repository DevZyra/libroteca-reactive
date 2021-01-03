package pl.devzyra.librotecareactivestack.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pl.devzyra.librotecareactivestack.handlers.LoginHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class LoginRouter {


    @Bean
    RouterFunction<ServerResponse> loginRoutes(LoginHandler loginHandler) {

        return route()
                .POST("/login", loginHandler::loginUser)
                .build();
    }


}

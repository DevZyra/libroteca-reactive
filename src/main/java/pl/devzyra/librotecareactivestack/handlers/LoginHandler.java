package pl.devzyra.librotecareactivestack.handlers;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pl.devzyra.librotecareactivestack.config.JwtUtils;
import pl.devzyra.librotecareactivestack.dtos.requests.UserLoginRequest;
import pl.devzyra.librotecareactivestack.services.UserService;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class LoginHandler {

    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;


    public Mono<ServerResponse> loginUser(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(UserLoginRequest.class).flatMap(userService::checkIfUserIsValid).flatMap(validUser -> ServerResponse.ok().bodyValue(jwtUtils.generateToken(validUser)));

    }
}

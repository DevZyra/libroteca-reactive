package pl.devzyra.librotecareactivestack.config;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.devzyra.librotecareactivestack.services.UserService;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtUtils jwtUtils;
    private final UserService userService;


    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        String username = jwtUtils.extractUsername(token);

        return userService.findByUsername(username)
                .flatMap(userDetails -> {
                    if(jwtUtils.validateToken(token,userDetails)){
                        return Mono.just(authentication);
                    } else {
                        return Mono.empty();
                    }
                });
    }
}

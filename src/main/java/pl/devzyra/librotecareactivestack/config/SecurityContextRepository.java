package pl.devzyra.librotecareactivestack.config;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private final String BEARER_PREFIX = "Bearer ";

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.error(UnsupportedOperationException::new);
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange swe) {

        return Mono.justOrEmpty(swe.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(b -> b.startsWith(BEARER_PREFIX))
                .map(subs -> subs.substring(BEARER_PREFIX.length()))
                .flatMap(token -> Mono.just(jwtUtils.getAuthentication(token)))
                .flatMap(auth -> authenticationManager.authenticate(auth).map(SecurityContextImpl::new));

    }
}

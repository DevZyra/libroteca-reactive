package pl.devzyra.librotecareactivestack.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@AllArgsConstructor
public class WebfluxSecurityConfig {
//ewentualnie Bean i Reactive UserDetailsServs
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;


    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        http.authorizeExchange( authorizeConfig -> authorizeConfig
                .pathMatchers("/login").permitAll()
                .anyExchange().authenticated()
                )
                .exceptionHandling()
                .authenticationEntryPoint(
                        (swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED))
                )
                .accessDeniedHandler(
                        (swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN))
                )
                .and()
                .formLogin().disable()
                .csrf().disable()
                .httpBasic().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .requestCache().requestCache(NoOpServerRequestCache.getInstance());

        return http.build();
    }

 /*   @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$10$X/GEwCWons1vyPEz08UaUer85yXPApzc8MuCYTghx4XqqgNuP5QFS")
                .roles("ADMIN")
                .build();
        return new MapReactiveUserDetailsService(admin);
    }*/

}

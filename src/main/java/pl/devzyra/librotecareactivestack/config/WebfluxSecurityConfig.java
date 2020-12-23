package pl.devzyra.librotecareactivestack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class WebfluxSecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        http.authorizeExchange()
                .anyExchange().authenticated()
                .and().httpBasic();

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

package pl.devzyra.librotecareactivestack.services;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import pl.devzyra.librotecareactivestack.dtos.requests.UserLoginRequest;
import pl.devzyra.librotecareactivestack.entities.UserDocument;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService extends ReactiveUserDetailsService {

    Mono<UserDocument> getUserById(String id);

    Flux<UserDocument> getAllUsers();

    Mono<UserDocument> saveUser(UserDocument userDocument);

    Mono<UserDocument> deleteUser(String id);

    Mono<UserDetails> checkIfUserIsValid(UserLoginRequest userLoginRequest);
}

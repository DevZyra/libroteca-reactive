package pl.devzyra.librotecareactivestack.services;

import pl.devzyra.librotecareactivestack.entities.UserDocument;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserDocument> getUserById(String id);

    Flux<UserDocument> getAllUsers();

    Mono<UserDocument> saveUser(UserDocument userDocument);
}

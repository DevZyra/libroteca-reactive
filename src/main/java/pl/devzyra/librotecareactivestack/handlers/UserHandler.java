package pl.devzyra.librotecareactivestack.handlers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pl.devzyra.librotecareactivestack.entities.UserDocument;
import pl.devzyra.librotecareactivestack.repositories.UserReactiveRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {


    private final UserReactiveRepository userRepository;

    public UserHandler(UserReactiveRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<ServerResponse> getUser(ServerRequest serverRequest) {

        String id = serverRequest.pathVariable("id");
        Mono<UserDocument> userById = userRepository.findById(id);

        return userById.flatMap(user -> ServerResponse.ok()
                .body(userById, UserDocument.class)).switchIfEmpty(Mono.defer(() -> ServerResponse.notFound().build()));

    }

    public Mono<ServerResponse> getAllUsers(ServerRequest serverRequest) {

        Flux<UserDocument> allUsers = userRepository.findAll();

        return ServerResponse.ok().body(allUsers, UserDocument.class);
    }

}

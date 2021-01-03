package pl.devzyra.librotecareactivestack.handlers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pl.devzyra.librotecareactivestack.dtos.UserDto;
import pl.devzyra.librotecareactivestack.entities.UserDocument;
import pl.devzyra.librotecareactivestack.repositories.UserElasticReactiveRepository;
import pl.devzyra.librotecareactivestack.services.UserService;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class UserHandler {


    private final UserService userService;
    private final UserElasticReactiveRepository userElasticReactiveRepository;

    public UserHandler(UserService userService, UserElasticReactiveRepository userElasticReactiveRepository) {
        this.userService = userService;
        this.userElasticReactiveRepository = userElasticReactiveRepository;
    }


    public Mono<ServerResponse> getUser(ServerRequest serverRequest) {

        String id = serverRequest.pathVariable("id");
        Mono<UserDocument> userById = userService.getUserById(id);

        return userById.flatMap(user -> ServerResponse.ok()
                .body(userById, UserDocument.class)).switchIfEmpty(Mono.defer(() -> ServerResponse.notFound().build()));

    }

    public Mono<ServerResponse> getAllUsers(ServerRequest serverRequest) {


        return ServerResponse.ok().body(userService.getAllUsers(), UserDocument.class);
    }

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {

        Mono<UserDocument> userMono = serverRequest.bodyToMono(UserDocument.class);

        return userMono
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON).body(userService.saveUser(user), UserDocument.class));


    }

    public Mono<ServerResponse> updateUser(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        return serverRequest.bodyToMono(UserDto.class)
                .flatMap(userDto -> userService.updateUser(id,userDto))
                .flatMap(userResponse -> ServerResponse.ok().bodyValue(userResponse)).switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> deleteUser(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        return userService.deleteUser(id).flatMap(response -> ServerResponse.noContent().build()).switchIfEmpty(ServerResponse.notFound().build());
    }
}

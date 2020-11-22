package pl.devzyra.librotecareactivestack.handlers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pl.devzyra.librotecareactivestack.entities.UserDocument;
import pl.devzyra.librotecareactivestack.repositories.UserReactiveRepository;
import pl.devzyra.librotecareactivestack.services.UserService;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {


    private final UserService userService;
    private final UserReactiveRepository userReactiveRepository;

    public UserHandler(UserService userService, UserReactiveRepository userReactiveRepository) {
        this.userService = userService;
        this.userReactiveRepository = userReactiveRepository;
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

        Mono<UserDocument> userUpdated = serverRequest.bodyToMono(UserDocument.class)
                .flatMap(
                        userUpd -> {
                            return userService.getUserById(id).flatMap(
                                    userDb -> {
                                        userDb.setFirstname(userUpd.getFirstname());
                                        userDb.setLastname(userUpd.getLastname());
                                        userDb.setEmail(userUpd.getEmail());
                                        userDb.setAddress(userUpd.getAddress());
                                        return userService.saveUser(userDb);
                                    });
                        });

        return userUpdated.flatMap(response -> ServerResponse.ok().body(BodyInserters.fromValue(response))).switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteUser(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        return userService.deleteUser(id).flatMap(response -> ServerResponse.noContent().build()).switchIfEmpty(ServerResponse.notFound().build());
    }
}

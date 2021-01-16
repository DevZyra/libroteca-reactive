package pl.devzyra.librotecareactivestack.handlers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pl.devzyra.librotecareactivestack.dtos.UserDto;
import pl.devzyra.librotecareactivestack.entities.UserDocument;
import pl.devzyra.librotecareactivestack.services.UserService;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {


    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
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

    public Mono<ServerResponse> getUsersPaged(ServerRequest serverRequest) {
        int page = Integer.parseInt(serverRequest.queryParam("page").orElse("0"));
        int limit = Integer.parseInt(serverRequest.queryParam("limit").orElse("25"));

        return ServerResponse.ok().body(userService.getUsersPaged(page, limit), UserDocument.class);
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
                .flatMap(userDto -> userService.updateUser(id, userDto))
                .flatMap(userResponse -> ServerResponse.ok().bodyValue(userResponse))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> deleteUser(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");

        return userService.deleteUser(id).flatMap(response -> ServerResponse.noContent().build()).switchIfEmpty(ServerResponse.notFound().build());
    }
}

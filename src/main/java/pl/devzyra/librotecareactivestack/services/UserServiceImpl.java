package pl.devzyra.librotecareactivestack.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.devzyra.librotecareactivestack.entities.UserDocument;
import pl.devzyra.librotecareactivestack.repositories.UserReactiveRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class UserServiceImpl implements pl.devzyra.librotecareactivestack.services.UserService {


    private final UserReactiveRepository userRepository;

    public UserServiceImpl(UserReactiveRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<UserDocument> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Flux<UserDocument> getAllUsers() {
        return userRepository.findAll().log();
    }

    @Override
    public Mono<UserDocument> saveUser(UserDocument user) {
        return userRepository.save(user);
    }

    @Override
    public Mono<UserDocument> deleteUser(String id) {

        return userRepository.findById(id)
                .flatMap(user -> userRepository.delete(user).then(Mono.just(user)));
    }
}

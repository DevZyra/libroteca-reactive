package pl.devzyra.librotecareactivestack.services;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.devzyra.librotecareactivestack.dtos.requests.UserLoginRequest;
import pl.devzyra.librotecareactivestack.entities.UserDocument;
import pl.devzyra.librotecareactivestack.repositories.UserElasticReactiveRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional

public class UserServiceImpl implements UserService {


    private final UserElasticReactiveRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserElasticReactiveRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

    @Override
    public Mono<UserDetails> checkIfUserIsValid(UserLoginRequest userLoginRequest) {

        return userRepository.findByEmail(userLoginRequest.getUsername()).cast(UserDocument.class).flatMap(user -> {
            if (passwordEncoder.matches(userLoginRequest.getPassword(), user.getEncryptedPassword())) {
                return Mono.just(user);
            } else {
                return Mono.error(new BadCredentialsException("Provided credentials does not match"));
            }
        });
    }

    @Override
    public Mono<UserDetails> findByUsername(String email) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new UsernameNotFoundException("User with provided email does not exist."))));
    }
}

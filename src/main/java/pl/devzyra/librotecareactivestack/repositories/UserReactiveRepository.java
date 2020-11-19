package pl.devzyra.librotecareactivestack.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pl.devzyra.librotecareactivestack.entities.UserDocument;

public interface UserReactiveRepository extends ReactiveMongoRepository<UserDocument,String> {
}

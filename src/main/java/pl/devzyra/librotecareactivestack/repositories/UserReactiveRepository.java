package pl.devzyra.librotecareactivestack.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pl.devzyra.librotecareactivestack.entities.UserDocument;

@Repository
public interface UserReactiveRepository extends ReactiveMongoRepository<UserDocument, String> {
}

package pl.devzyra.librotecareactivestack.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pl.devzyra.librotecareactivestack.entities.BookDocument;

@Repository
public interface BookReactiveRepository extends ReactiveMongoRepository<BookDocument, String> {
}

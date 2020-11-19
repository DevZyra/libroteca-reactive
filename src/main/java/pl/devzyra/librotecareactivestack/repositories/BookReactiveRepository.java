package pl.devzyra.librotecareactivestack.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pl.devzyra.librotecareactivestack.entities.BookDocument;


public interface BookReactiveRepository extends ReactiveMongoRepository<BookDocument,String> {
}

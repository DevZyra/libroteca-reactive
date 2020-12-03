package pl.devzyra.librotecareactivestack.repositories;

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;
import pl.devzyra.librotecareactivestack.entities.BookDocument;

@Repository
public interface BookElasticReactiveRepository extends ReactiveElasticsearchRepository<BookDocument, String> {
}

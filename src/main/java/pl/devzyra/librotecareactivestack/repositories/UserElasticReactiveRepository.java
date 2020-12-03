package pl.devzyra.librotecareactivestack.repositories;

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;
import pl.devzyra.librotecareactivestack.entities.UserDocument;

@Repository
public interface UserElasticReactiveRepository extends ReactiveElasticsearchRepository<UserDocument, String> {
}

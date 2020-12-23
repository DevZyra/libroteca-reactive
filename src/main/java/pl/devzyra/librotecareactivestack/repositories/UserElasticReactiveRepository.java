package pl.devzyra.librotecareactivestack.repositories;

import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import pl.devzyra.librotecareactivestack.entities.UserDocument;
import reactor.core.publisher.Mono;

@Repository
public interface UserElasticReactiveRepository extends ReactiveElasticsearchRepository<UserDocument, String> {

    Mono<UserDetails> findByEmail(String email);
}

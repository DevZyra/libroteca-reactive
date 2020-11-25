package pl.devzyra.librotecareactivestack.services;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import pl.devzyra.librotecareactivestack.entities.BookDocument;
import reactor.core.publisher.Flux;

@Service
public class SearchBookServiceImpl implements SearchBookService {


    private final ReactiveMongoTemplate mongoTemplate;

    public SearchBookServiceImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public Flux<BookDocument> searchByTitle(String title) {
        Query query = new Query();

        query.addCriteria(Criteria.where("title").regex(title, "i"));
        return mongoTemplate.find(query, BookDocument.class);
    }
}

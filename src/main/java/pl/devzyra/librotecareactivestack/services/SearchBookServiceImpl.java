package pl.devzyra.librotecareactivestack.services;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import pl.devzyra.librotecareactivestack.entities.BookDocument;
import reactor.core.publisher.Flux;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

@Service
public class SearchBookServiceImpl implements SearchBookService {

    private final ReactiveElasticsearchOperations reactiveElasticsearchOperations;

    public SearchBookServiceImpl(ReactiveElasticsearchOperations reactiveElasticsearchOperations) {
        this.reactiveElasticsearchOperations = reactiveElasticsearchOperations;
    }

    @Override
    public Flux<BookDocument> searchByTitle(String title) {


        QueryBuilder boolQuery = QueryBuilders.boolQuery()
                .should(queryStringQuery(title).field("title").lenient(true))
                .should(queryStringQuery("*" + title + "*").field("title").lenient(true));

        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(boolQuery).build();


        return reactiveElasticsearchOperations.search(query, BookDocument.class).map(SearchHit::getContent);
    }

    @Override
    public Flux<BookDocument> searchByAuthor(String name) {
        return null;
    }
}

package pl.devzyra.librotecareactivestack.services;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import pl.devzyra.librotecareactivestack.entities.BookDocument;
import reactor.core.publisher.Flux;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Service
public class SearchBookServiceImpl implements SearchBookService {

    private final ReactiveElasticsearchOperations reactiveElasticsearchOperations;

    public SearchBookServiceImpl(ReactiveElasticsearchOperations reactiveElasticsearchOperations) {
        this.reactiveElasticsearchOperations = reactiveElasticsearchOperations;
    }

    @Override
    public Flux<BookDocument> searchByTitle(String title) {

        // info: Not the most performant way - using wildcards, but support partial-word search
        QueryBuilder boolQuery = boolQuery()
                .should(queryStringQuery(title).field("title").lenient(true))
                .should(queryStringQuery("*" + title + "*").field("title").lenient(true));

        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(boolQuery).build();

        return reactiveElasticsearchOperations.search(query, BookDocument.class).map(SearchHit::getContent);
    }

    @Override
    public Flux<BookDocument> searchByAuthor(String name) {

        final String AUTHORS_NAME = "authors.name";

        QueryBuilder test = nestedQuery("authors", boolQuery()
                .should(queryStringQuery(name).field(AUTHORS_NAME).lenient(true))
                .should(queryStringQuery("*" + name + "*").field(AUTHORS_NAME)), ScoreMode.None);

        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(test).build();

        return reactiveElasticsearchOperations.search(query, BookDocument.class).map(SearchHit::getContent);
    }
}

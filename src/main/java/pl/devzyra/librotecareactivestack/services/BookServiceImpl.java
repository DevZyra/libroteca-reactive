package pl.devzyra.librotecareactivestack.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.devzyra.librotecareactivestack.entities.BookDocument;
import pl.devzyra.librotecareactivestack.mappers.BookMapper;
import pl.devzyra.librotecareactivestack.repositories.BookElasticReactiveRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookElasticReactiveRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookElasticReactiveRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public Mono<BookDocument> getBookById(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public Flux<BookDocument> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Flux<BookDocument> getBooksPaged(int page, int limit) {
        return bookRepository.findAll()
                .sort(Comparator.comparing(BookDocument::getTitle).reversed())
                .skip(page * limit)
                .take(limit);
    }

    @Override
    public Mono<BookDocument> saveBook(BookDocument bookDocument) {
        return bookRepository.save(bookDocument);
    }

    @Override
    public Mono<BookDocument> deleteBook(String id) {

        return bookRepository.findById(id)
                .flatMap(book -> bookRepository.delete(book).then(Mono.just(book)));
    }

    @Override
    public Mono<BookDocument> updateBook(String id, BookDocument bookDocument) {
        return bookRepository.findById(id)
                .map(book -> bookMapper.mapToBook(book, bookDocument))
                .flatMap(bookRepository::save);
    }
}

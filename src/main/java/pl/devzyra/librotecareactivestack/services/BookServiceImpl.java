package pl.devzyra.librotecareactivestack.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.devzyra.librotecareactivestack.entities.BookDocument;
import pl.devzyra.librotecareactivestack.repositories.BookReactiveRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookReactiveRepository bookRepository;

    public BookServiceImpl(BookReactiveRepository bookRepository) {
        this.bookRepository = bookRepository;
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
    public Mono<BookDocument> saveBook(BookDocument bookDocument) {
        return null;
    }

    @Override
    public Mono<BookDocument> deleteBook(String id) {

        return null;
    }
}

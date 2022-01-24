package ru.vluzhnykh.quickstart.migration.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vluzhnykh.quickstart.migration.domain.Book;
import ru.vluzhnykh.quickstart.migration.repository.BookRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository repository;

    @Override
    public Book add(Book book) {
        return repository.save(book);
    }

    @Override
    public Optional<Book> get(long bookId) {
        return repository.findById(bookId);
    }

    @Override
    public boolean replace(Book book) {
        if (repository.existsById(book.getId())) {
            repository.save(book);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Long bookId) {
        if (repository.existsById(bookId)) {
            repository.deleteById(bookId);
            return true;
        } else {
            return false;
        }
    }

}

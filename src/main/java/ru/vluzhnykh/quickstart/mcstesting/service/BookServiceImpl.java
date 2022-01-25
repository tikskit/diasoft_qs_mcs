package ru.vluzhnykh.quickstart.mcstesting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vluzhnykh.quickstart.mcstesting.domain.Book;
import ru.vluzhnykh.quickstart.mcstesting.repository.BookRepository;

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
    @Transactional
    public boolean replace(Book book) {
        if (repository.existsById(book.getId())) {
            repository.save(book);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean delete(Long bookId) {
        if (repository.existsById(bookId)) {
            repository.deleteById(bookId);
            return true;
        } else {
            return false;
        }
    }

}

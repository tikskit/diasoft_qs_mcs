package ru.vluzhnykh.quickstart.mcstesting.service;

import ru.vluzhnykh.quickstart.mcstesting.domain.Book;

import java.util.Optional;

public interface BookService {
    Book add(Book book);

    Optional<Book> get(long bookId);

    boolean replace(Book book);

    boolean delete(Long bookId);
}

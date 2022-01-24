package ru.vluzhnykh.quickstart.rest.service;

import org.springframework.stereotype.Service;
import ru.vluzhnykh.quickstart.rest.controller.BookDto;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class BookStore {
    private long lastId;
    private Map<Long, BookDto> books = new HashMap<>();

    public synchronized BookDto add(BookDto bookDto) {
        long bookId = ++lastId;
        BookDto newBook = new BookDto(bookId, bookDto.getName(), bookDto.getAuthor());
        books.put(bookId, newBook);
        return newBook;
    }

    public synchronized Optional<BookDto> get(long bookId) {
        return Optional.ofNullable(books.get(bookId));
    }

    public synchronized boolean replace(BookDto bookDto) {
        if (books.containsKey(bookDto.getId())) {
            books.put(bookDto.getId(), bookDto);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean delete(Long bookId) {
        return books.remove(bookId) != null;
    }
}

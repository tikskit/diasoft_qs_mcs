package ru.vluzhnykh.quickstart.migration.rest;

import org.springframework.stereotype.Component;
import ru.vluzhnykh.quickstart.migration.domain.Book;

@Component
public class BookConverter {
    public Book toBook(BookDto dto) {
        return new Book(dto.getId(), dto.getTitle(), dto.getAuthor());
    }

    public BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor());
    }
}

package ru.vluzhnykh.quickstart.mcstesting.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vluzhnykh.quickstart.mcstesting.domain.Book;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Конвертер сущности Book должен")
@SpringBootTest(classes = BookConverter.class)
class BookConverterTest {
    @Autowired
    private BookConverter bookConverter;

    @DisplayName("правильно конвертировать сущность в dto")
    @Test
    public void shouldCorrectlyConvertEntity2Dto() {
        Book book = new Book(10L, "title", "author");
        BookDto bookDto = bookConverter.toDto(book);
        assertThat(bookDto.getId()).isEqualTo(book.getId());
        assertThat(bookDto.getTitle()).isEqualTo(book.getTitle());
        assertThat(bookDto.getAuthor()).isEqualTo(book.getAuthor());
    }

    @DisplayName("правильно конвертировать dto в сущность")
    @Test
    public void shouldCorrectlyConvertDto2Entity() {
        BookDto bookDto = new BookDto(10L, "title", "author");
        Book book = bookConverter.toBook(bookDto);
        assertThat(book.getId()).isEqualTo(bookDto.getId());
        assertThat(book.getTitle()).isEqualTo(bookDto.getTitle());
        assertThat(book.getAuthor()).isEqualTo(bookDto.getAuthor());
    }
}
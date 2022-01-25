package ru.vluzhnykh.quickstart.mcstesting.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import ru.vluzhnykh.quickstart.mcstesting.domain.Book;
import ru.vluzhnykh.quickstart.mcstesting.repository.BookRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@DisplayName("Сервис книг должен")
@Import(BookServiceImpl.class)
@DataJpaTest
class BookServiceImplTest {
    @MockBean
    private BookRepository bookRepository;
    @Autowired
    private BookServiceImpl bookService;

    @DisplayName("сохранять книги, используя репозиторий книг")
    @Test
    public void shouldSaveBookUsingBookRepository() {
        Book book = new Book(0L, "title", "author");

        when(bookRepository.save(book)).thenReturn(new Book(1L, book.getTitle(), book.getAuthor()));

        bookService.add(book);

        verify(bookRepository, times(1)).save(book);
        verifyNoMoreInteractions(bookRepository);
    }

    @DisplayName("возвращать книгу по идентификатору")
    @Test
    public void shouldReturnBookById() {
        final long bookId = 1L;
        Book excepted = new Book(bookId, "title", "author");
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(excepted));

        Optional<Book> actual = bookService.get(bookId);
        assertThat(actual).isPresent().get().isEqualTo(excepted);

        verify(bookRepository, times(1)).findById(bookId);
        verifyNoMoreInteractions(bookRepository);
    }

    @DisplayName("обновлять книгу в БД, если она есть")
    @Test
    public void shouldUpdateBookIfExists() {
        final long bookId = 1L;
        Book book = new Book(bookId, "title", "author");
        when(bookRepository.existsById(bookId)).thenReturn(true);

        assertThat(bookService.replace(book)).isTrue();

        verify(bookRepository, times(1)).existsById(bookId);
        verify(bookRepository, times(1)).save(book);
        verifyNoMoreInteractions(bookRepository);
    }

    @DisplayName("не обновлять книгу в БД, если ее нет")
    @Test
    public void shouldNotUpdateBookIfNotExist() {
        final long bookId = 1L;
        Book book = new Book(bookId, "title", "author");
        when(bookRepository.existsById(bookId)).thenReturn(false);

        assertThat(bookService.replace(book)).isFalse();

        verify(bookRepository, times(1)).existsById(bookId);
        verifyNoMoreInteractions(bookRepository);
    }

    @DisplayName("удалять книгу, если она есть, и возвращать true")
    @Test
    public void shouldDeleteBookIfExistsAndReturnTrue() {
        final long bookId = 1L;
        when(bookRepository.existsById(bookId)).thenReturn(true);

        assertThat(bookService.delete(bookId)).isTrue();

        verify(bookRepository, times(1)).existsById(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
        verifyNoMoreInteractions(bookRepository);
    }

    @DisplayName("удалять книгу, если ее нет, и возвращать false")
    @Test
    public void shouldNotDeleteBookIfNotExistAndReturnFalse() {
        final long bookId = 1L;
        when(bookRepository.existsById(bookId)).thenReturn(false);

        assertThat(bookService.delete(bookId)).isFalse();

        verify(bookRepository, times(1)).existsById(bookId);
        verifyNoMoreInteractions(bookRepository);
    }
}
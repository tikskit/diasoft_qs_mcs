package ru.vluzhnykh.quickstart.mcstesting.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.vluzhnykh.quickstart.mcstesting.domain.Book;
import ru.vluzhnykh.quickstart.mcstesting.service.BookServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер книг должен")
@WebMvcTest
@Import({BookServiceImpl.class, BookConverter.class})
class BookControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private BookServiceImpl bookService;
    @Autowired
    private BookConverter bookConverter;

    @DisplayName("добавлять книгу по POST /books")
    @Test
    public void shouldAddBookAtPostBooks() throws Exception {
        Book newBook = new Book(0L, "title", "author");
        Book persistedBook = new Book(1L, newBook.getTitle(), newBook.getAuthor());
        when(bookService.add(newBook)).thenReturn(persistedBook);

        ObjectMapper mapper = new ObjectMapper();
        String persistedBooksJson = mapper.writeValueAsString(persistedBook);
        String newBooksJson = mapper.writeValueAsString(newBook);

        mvc.perform(
                post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newBooksJson)
        )
                .andExpect(status().isCreated())
                .andExpect(content().json(persistedBooksJson));

        verify(bookService, times(1)).add(newBook);
        verifyNoMoreInteractions(bookService);
    }

    @DisplayName("возвращать книгу по GET /books/{bookId}")
    @Test
    public void shouldReturnBookAtGetBooksId() throws Exception {
        final long bookId = 1L;
        Book book = new Book(bookId, "title", "author");
        BookDto bookDto = bookConverter.toDto(book);

        when(bookService.get(bookId)).thenReturn(Optional.of(book));

        ObjectMapper mapper = new ObjectMapper();
        String bookJson = mapper.writeValueAsString(bookDto);

        mvc.perform(
                get("/books/" + bookId)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(bookJson));

        verify(bookService, times(1)).get(bookId);
        verifyNoMoreInteractions(bookService);
    }

    @DisplayName("возвращать 404 по GET /books/{bookId}, если книги не существует")
    @Test
    public void shouldReturn404AtGetBooksIdWhenBookNotFound() throws Exception {
        when(bookService.get(anyLong())).thenReturn(Optional.empty());

        mvc.perform(
                get("/books/" + 404L)
        )
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).get(anyLong());
        verifyNoMoreInteractions(bookService);
    }

    @DisplayName("возвращать 200 по PUT /books, если обновляемая книга найдена в БД")
    @Test
    public void shouldUpdateBookWhenExists() throws Exception {
        Book book = new Book(1L, "title", "author");
        when(bookService.replace(book)).thenReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        String bookJson = mapper.writeValueAsString(book);

        mvc.perform(
                put("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        )
                .andExpect(status().isOk());

        verify(bookService, times(1)).replace(book);
        verifyNoMoreInteractions(bookService);
    }

    @DisplayName("возвращать 404 по PUT /books, если обновляемая книга не найдена в БД")
    @Test
    public void shouldReturn404WhenUpdatingBookNotExist() throws Exception {
        Book book = new Book(1L, "title", "author");
        when(bookService.replace(book)).thenReturn(false);

        ObjectMapper mapper = new ObjectMapper();
        String bookJson = mapper.writeValueAsString(book);

        mvc.perform(
                put("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        )
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).replace(book);
        verifyNoMoreInteractions(bookService);
    }

    @DisplayName("удалять книгу по DELETE /books/{bookId}, если она существует")
    @Test
    public void shouldDeleteBookWhenExists() throws Exception {
        long bookId = 1L;
        when(bookService.delete(bookId)).thenReturn(true);

        mvc.perform(
                delete("/books/" + bookId)
        )
                .andExpect(status().isOk());

        verify(bookService, times(1)).delete(bookId);
        verifyNoMoreInteractions(bookService);
    }

    @DisplayName("возвращать 404 DELETE /books/{bookId}, если она не существует")
    @Test
    public void shouldReturn404WhenDeletingBookNotExists() throws Exception {
        long bookId = 1L;
        when(bookService.delete(bookId)).thenReturn(false);

        mvc.perform(
                delete("/books/" + bookId)
        )
                .andExpect(status().isNotFound());

        verify(bookService, times(1)).delete(bookId);
        verifyNoMoreInteractions(bookService);
    }
}
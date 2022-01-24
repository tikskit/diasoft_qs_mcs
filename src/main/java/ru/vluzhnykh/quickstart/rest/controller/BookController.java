package ru.vluzhnykh.quickstart.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.vluzhnykh.quickstart.rest.service.BookStore;

@RestController
@AllArgsConstructor
public class BookController {

    private final BookStore bookStore;

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("/books")
    public @ResponseBody BookDto add(@RequestBody BookDto book) {
        return bookStore.add(book);
    }

    @GetMapping("/books/{bookId}")
    public @ResponseBody BookDto get(@PathVariable(name = "bookId") Long bookId) {
        return bookStore.get(bookId).orElseThrow(BookNotFound::new);
    }

    @PutMapping("/books")
    public @ResponseBody BookDto update(@RequestBody BookDto book) {
        if (bookStore.replace(book)) {
            return book;
        } else {
            throw new BookNotFound();
        }
    }

    @DeleteMapping("/books/{bookId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable(name = "bookId") Long bookId) {
        if (!bookStore.delete(bookId)) {
            throw new BookNotFound();
        }
    }
}

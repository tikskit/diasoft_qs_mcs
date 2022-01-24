package ru.vluzhnykh.quickstart.migration.rest;

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
import ru.vluzhnykh.quickstart.migration.service.BookService;

@RestController
@AllArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookConverter bookConverter;

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("/books")
    public @ResponseBody BookDto add(@RequestBody BookDto bookDto) {
        return bookConverter.toDto(bookService.add(bookConverter.toBook(bookDto)));
    }

    @GetMapping("/books/{bookId}")
    public @ResponseBody BookDto get(@PathVariable(name = "bookId") Long bookId) {
        return bookConverter.toDto(bookService.get(bookId).orElseThrow(BookNotFound::new));
    }

    @PutMapping("/books")
    public @ResponseBody BookDto update(@RequestBody BookDto bookDto) {
        if (bookService.replace(bookConverter.toBook(bookDto))) {
            return bookDto;
        } else {
            throw new BookNotFound();
        }
    }

    @DeleteMapping("/books/{bookId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable(name = "bookId") Long bookId) {
        if (!bookService.delete(bookId)) {
            throw new BookNotFound();
        }
    }
}

package com.yeonnex.catalogservice.web;

import com.yeonnex.catalogservice.domain.Book;
import com.yeonnex.catalogservice.domain.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    Iterable<Book> findAllBooks() {
        return bookService.viewBookList();
    }

    @GetMapping("/{isbn}")
    Book findByIsbn(@PathVariable String isbn) {
        return bookService.viewBookDetails(isbn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@RequestBody @Valid Book book) {
        return bookService.addBookToCatalog(book);
    }

    @PutMapping("/{isbn}")
    public Book updateBook(@PathVariable String isbn, @RequestBody @Valid Book book) {
        return bookService.editBookDetails(isbn, book);
    }
}

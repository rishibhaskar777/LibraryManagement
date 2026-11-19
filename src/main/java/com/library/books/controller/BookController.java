package com.library.books.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.books.model.Book;
import com.library.books.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    // (b) Add book -> 201 Created (400 if invalid)
    @PostMapping
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addBook(book));
    }

    // (c) All books -> 200 OK
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(service.getAllBooks());
    }

    // (c) Book by id -> 200 OK, or 404 (handled in GlobalExceptionHandler)
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        return ResponseEntity.ok(service.getBookById(id));
    }

    // (d) Update title, author, category, available copies -> 200 OK, or 404
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @Valid @RequestBody Book book) {
        return ResponseEntity.ok(service.updateBook(id, book));
    }

    // (e) Delete -> 200 OK with message, or 404
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteBook(@PathVariable int id) {
        service.deleteBook(id);
        return ResponseEntity.ok(Map.of("message", "Book with id " + id + " deleted successfully"));
    }

    // ----- Extra endpoints (collections) -----

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Book>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(service.getBooksByCategory(category));
    }

    @GetMapping("/copies-by-category")
    public ResponseEntity<Map<String, Integer>> copiesByCategory() {
        return ResponseEntity.ok(service.copiesByCategory());
    }
}

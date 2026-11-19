package com.library.books.exception;

// Thrown when no book has the given id -> mapped to HTTP 404
public class BookNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BookNotFoundException(int id) {
        super("Book not found with id " + id);
    }
}

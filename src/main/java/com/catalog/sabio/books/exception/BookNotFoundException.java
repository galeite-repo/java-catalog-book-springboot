package com.catalog.sabio.books.exception;


public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long id) {
        super("Livro com ID " + id + " não encontrado.");
    }

    public BookNotFoundException(String message) {
        super(message);
    }
}
package com.catalog.sabio.books.service;

import com.catalog.sabio.books.exception.BookNotFoundException;
import com.catalog.sabio.books.model.BookEntity;
import com.catalog.sabio.books.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    public Page<BookEntity> getBooks(Pageable pageable) {
        Page<BookEntity> books = bookRepository.findAll(pageable);

        if (books.isEmpty()) {
            throw new BookNotFoundException("Nenhum livro encontrado");
        }

        return books;
    }

    @Cacheable(value = "books", key = "'getBooksAll'")
    public List<BookEntity> getBooksAll() {
        List<BookEntity> books = bookRepository.findAll();
        if (books.isEmpty()) {
            throw new BookNotFoundException("Nenhum livro encontrado");
        }
        System.out.println("getBooksByGenre (no cache)");
        return books;
    }


    public BookEntity getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Nenhum livro encontrado com o ID: " + id));
    }

    public List<String> getGenres() {
        return bookRepository.findDistinctGenre();
    }


    public List<BookEntity> getBooksByGenre(String genre) {
        List<BookEntity> books = bookRepository.findByGenreIgnoreCase(genre);
        if (books.isEmpty()) {
            throw new BookNotFoundException("Nenhum livro encontrado com o genero: " + genre);
        }
        return books;
    }

    public List<BookEntity> getBooksByAuthor(String author) {
        List<BookEntity> books = bookRepository.findByAuthorIgnoreCase(author);
        if (books.isEmpty()) {
            throw new BookNotFoundException("Nenhum livro encontrado com o author: " + author);
        }
        return books;
    }

}

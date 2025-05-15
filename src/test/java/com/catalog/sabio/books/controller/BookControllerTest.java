package com.catalog.sabio.books.controller;

import com.catalog.sabio.books.model.BookEntity;
import com.catalog.sabio.books.response.ApiResponse;
import com.catalog.sabio.books.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllBooks() {
        BookEntity book = new BookEntity();
        Page<BookEntity> books = new PageImpl<>(List.of(book));
        Pageable pageable = PageRequest.of(0, 10);

        when(bookService.getBooks(pageable)).thenReturn(books);

        ResponseEntity<ApiResponse<Page<BookEntity>>> response = bookController.getAllBooks(pageable);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("FOUND", response.getBody().getCode());
        assertEquals(1, response.getBody().getData().getTotalElements());
    }

    @Test
    void shouldReturnBookById() {
        Long bookId = 1L;
        BookEntity book = new BookEntity();
        when(bookService.getBookById(bookId)).thenReturn(book);

        ResponseEntity<BookEntity> response = bookController.getBookById(bookId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(book, response.getBody());
    }

    @Test
    void shouldReturnBooksByGenre() {
        String genre = "Fiction";
        List<BookEntity> books = List.of(new BookEntity());
        when(bookService.getBooksByGenre(genre)).thenReturn(books);

        ResponseEntity<ApiResponse<List<BookEntity>>> response = bookController.getBooksByCategory(genre);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("FOUND", response.getBody().getCode());
        assertEquals(1, response.getBody().getData().size());
    }

    @Test
    void shouldReturnBooksByAuthor() {
        String author = "Author Name";
        List<BookEntity> books = List.of(new BookEntity());
        when(bookService.getBooksByAuthor(author)).thenReturn(books);

        ResponseEntity<ApiResponse<List<BookEntity>>> response = bookController.getBooksByAuthor(author);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("FOUND", response.getBody().getCode());
        assertEquals(1, response.getBody().getData().size());
    }

    @Test
    void shouldReturnGenres() {
        List<String> genres = List.of("Fiction", "Drama");
        when(bookService.getGenres()).thenReturn(genres);

        ResponseEntity<ApiResponse<List<String>>> response = bookController.getCategories();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("FOUND", response.getBody().getCode());
        assertEquals(2, response.getBody().getData().size());
    }
}

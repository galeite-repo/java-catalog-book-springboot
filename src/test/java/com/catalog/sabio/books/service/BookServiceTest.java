package com.catalog.sabio.books.service;
import com.catalog.sabio.books.exception.BookNotFoundException;
import com.catalog.sabio.books.model.BookEntity;
import com.catalog.sabio.books.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnPageOfBooks() {
        var book = new BookEntity();
        var pageable = PageRequest.of(0, 10);
        var page = new PageImpl<>(List.of(book));

        when(bookRepository.findAll(pageable)).thenReturn(page);

        var result = bookService.getBooks(pageable);

        assertEquals(1, result.getContent().size());
        verify(bookRepository, times(1)).findAll(pageable);
    }

    @Test
    void shouldThrowExceptionWhenNoBooksFoundInPage() {
        var pageable = PageRequest.of(0, 10);
        when(bookRepository.findAll(pageable)).thenReturn(Page.empty());

        var ex = assertThrows(BookNotFoundException.class, () -> bookService.getBooks(pageable));
        assertEquals("Nenhum livro encontrado", ex.getMessage());
    }

    @Test
    void shouldReturnBookById() {
        var book = new BookEntity();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        var result = bookService.getBookById(1L);

        assertEquals(book, result);
        verify(bookRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenBookNotFoundById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        var ex = assertThrows(BookNotFoundException.class, () -> bookService.getBookById(1L));
        assertEquals("Nenhum livro encontrado com o ID: 1", ex.getMessage());
    }

    @Test
    void shouldReturnDistinctGenres() {
        List<String> genres = List.of("Drama", "Sci-Fi");
        when(bookRepository.findDistinctGenre()).thenReturn(genres);

        var result = bookService.getGenres();

        assertEquals(2, result.size());
        assertTrue(result.contains("Drama"));
    }

    @Test
    void shouldReturnBooksByGenre() {
        var book = new BookEntity();
        when(bookRepository.findByGenreIgnoreCase("Fantasy")).thenReturn(List.of(book));

        var result = bookService.getBooksByGenre("Fantasy");

        assertEquals(1, result.size());
        verify(bookRepository).findByGenreIgnoreCase("Fantasy");
    }

    @Test
    void shouldThrowExceptionWhenNoBooksFoundByGenre() {
        when(bookRepository.findByGenreIgnoreCase("Fantasy")).thenReturn(List.of());

        var ex = assertThrows(BookNotFoundException.class, () -> bookService.getBooksByGenre("Fantasy"));
        assertEquals("Nenhum livro encontrado com o genero: Fantasy", ex.getMessage());
    }

    @Test
    void shouldReturnBooksByAuthor() {
        var book = new BookEntity();
        when(bookRepository.findByAuthorIgnoreCase("Machado")).thenReturn(List.of(book));

        var result = bookService.getBooksByAuthor("Machado");

        assertEquals(1, result.size());
        verify(bookRepository).findByAuthorIgnoreCase("Machado");
    }

    @Test
    void shouldThrowExceptionWhenNoBooksFoundByAuthor() {
        when(bookRepository.findByAuthorIgnoreCase("Autor Inexistente")).thenReturn(List.of());

        var ex = assertThrows(BookNotFoundException.class, () -> bookService.getBooksByAuthor("Autor Inexistente"));
        assertEquals("Nenhum livro encontrado com o author: Autor Inexistente", ex.getMessage());
    }
}

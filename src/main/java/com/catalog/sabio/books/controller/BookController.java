package com.catalog.sabio.books.controller;

import com.catalog.sabio.books.model.BookEntity;
import com.catalog.sabio.books.response.ApiResponse;
import com.catalog.sabio.books.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<BookEntity>>> getAllBooks(Pageable pageable) {
        Page<BookEntity> books = bookService.getBooks(pageable);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "FOUND", books));
    }


    @GetMapping("/{id}")
    public ResponseEntity<BookEntity> getBookById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getBookById(id));
    }

    @GetMapping("/genre")
    public ResponseEntity<ApiResponse<List<BookEntity>>> getBooksByCategory(@RequestParam String genre) {
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "FOUND", bookService.getBooksByGenre(genre)));
    }


    @GetMapping("/author")
    public ResponseEntity<ApiResponse<List<BookEntity>>> getBooksByAuthor(@RequestParam String author) {
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "FOUND", bookService.getBooksByAuthor(author)));
    }


    @GetMapping("/genres")
    public ResponseEntity<ApiResponse<List<String>>> getCategories() {
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "FOUND", bookService.getGenres()));
    }
}

package com.catalog.sabio.books.controller;

import com.catalog.sabio.books.model.BookEntity;
import com.catalog.sabio.books.response.ApiResponseDto;
import com.catalog.sabio.books.response.BookListResponse;
import com.catalog.sabio.books.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Books", description = "Endpoints para gerenciamento de livros")
public class BookController {
    private final BookService bookService;


    @Operation(summary = "Listar todos os livros", description = "Retorna uma lista paginada de todos os livros disponíveis no catálogo.",
            responses = {@ApiResponse(responseCode = "200", description = "Livros encontrados com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookListResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida")})
    @GetMapping
    public ResponseEntity<ApiResponseDto<Page<BookEntity>>> getAllBooks(Pageable pageable) {
        Page<BookEntity> books = bookService.getBooks(pageable);
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK.value(), "FOUND", books));
    }


    @Operation(
            summary = "Buscar livro por ID",
            description = "Retorna os detalhes de um livro específico com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Livro encontrado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookEntity.class))),
                    @ApiResponse(responseCode = "404", description = "Livro não encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<BookEntity> getBookById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getBookById(id));
    }

    @Operation(
            summary = "Buscar livros por gênero",
            description = "Retorna uma lista de livros filtrados pelo gênero especificado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Livros encontrados com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookListResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida")
            }
    )
    @GetMapping("/genre")
    public ResponseEntity<ApiResponseDto<List<BookEntity>>> getBooksByCategory(@RequestParam String genre) {
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK.value(), "FOUND", bookService.getBooksByGenre(genre)));
    }


    @Operation(
            summary = "Buscar livros por autor",
            description = "Retorna uma lista de livros filtrados pelo autor especificado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Livros encontrados com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookListResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida")
            }
    )
    @GetMapping("/author")
    public ResponseEntity<ApiResponseDto<List<BookEntity>>> getBooksByAuthor(@RequestParam String author) {
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK.value(), "FOUND", bookService.getBooksByAuthor(author)));
    }

    @Operation(
            summary = "Listar gêneros distintos",
            description = "Retorna uma lista de gêneros únicos disponíveis no catálogo.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Gêneros encontrados com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida")
            }
    )
    @GetMapping("/genres")
    public ResponseEntity<ApiResponseDto<List<String>>> getCategories() {
        return ResponseEntity.ok(new ApiResponseDto<>(HttpStatus.OK.value(), "FOUND", bookService.getGenres()));
    }
}

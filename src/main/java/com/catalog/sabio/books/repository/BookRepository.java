package com.catalog.sabio.books.repository;

import com.catalog.sabio.books.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {


    List<BookEntity> findByGenreIgnoreCase(String genre);
    List<BookEntity> findByAuthorIgnoreCase(String author);

    @Query("SELECT DISTINCT b.genre FROM BookEntity b")
    List<String> findDistinctGenre();
}

package com.catalog.sabio.books.startup;

import com.catalog.sabio.books.model.BookEntity;
import com.catalog.sabio.books.repository.BookRepository;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

@Configuration
public class DatabaseSetup {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseSetup.class);
    private final BookRepository bookRepository;
    private final Faker faker = new Faker(new Locale("pt-BR"));

    public DatabaseSetup(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Bean
    @Transactional
    public CommandLineRunner initializeDatabase() {
        return args -> {
            long bookCount = bookRepository.count();
            int bookQuantity = 7000;

            if (bookCount < bookQuantity) {
                int booksToCreate = bookQuantity - (int) bookCount;
                logger.info("Gerando e inserindo {} novos livros fake...", booksToCreate);
                List<BookEntity> fakeBooks = generateFakeBooks(booksToCreate);
                bookRepository.saveAll(fakeBooks);
                logger.info("{} livros fake inseridos com sucesso.", booksToCreate);
            } else {
                logger.info("O banco de dados contem {} livros.", bookCount);
            }
        };
    }

    private List<BookEntity> generateFakeBooks(int count) {
        return IntStream.rangeClosed(1, count)
                .mapToObj(i -> {
                    String title = faker.book().title();
                    String author = faker.book().author();
                    String genre = faker.book().genre();

                    StringBuilder descriptionBuilder = new StringBuilder();
                    IntStream.rangeClosed(1, faker.number().numberBetween(3, 7))
                            .forEach(j -> descriptionBuilder.append(faker.lorem().paragraph(faker.number().numberBetween(5, 15))).append("\n\n"));
                    String description = descriptionBuilder.toString().trim();
                    return BookEntity.builder()
                            .title(title)
                            .author(author)
                            .description(description)
                            .genre(genre)
                            .build();
                })
                .toList();
    }
}
package ru.otus.homework.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Прохоренко Виктор
 */
@DisplayName("Репозиторий книг должен: ")
@DataMongoTest
@ExtendWith(SpringExtension.class)
public class BookRepositoryTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void init(){
        Author author1 = new Author("1", "author1");
        mongoTemplate.save(author1, "authors");

        Genre genre1 = new Genre("1", "genre1");
        mongoTemplate.save(genre1, "genres");

        Book expectedBook1 = new Book("1", "book1", author1, genre1);

        Author author2 = new Author("2", "author2");
        mongoTemplate.save(author2, "authors");

        Genre genre2 = new Genre("2", "genre2");
        mongoTemplate.save(genre2, "genres");

        Book expectedBook2 = new Book("2", "book2", author2, genre2);

        Author author3 = new Author("3", "author3");
        mongoTemplate.save(author3, "authors");

        Genre genre3 = new Genre("3", "genre3");
        mongoTemplate.save(genre3, "genres");

        Book expectedBook3 = new Book("3", "book3", author3, genre3);

        mongoTemplate.save(expectedBook1, "books");
        mongoTemplate.save(expectedBook2, "books");
        mongoTemplate.save(expectedBook3, "books");
    }

    @Test
    @DisplayName("Найти книгу по id")
    void shouldReturnBookById(){
        Author author1 = new Author("1", "author1");
        Genre genre1 = new Genre("1", "genre1");
        Book expected = new Book("1", "book1", author1, genre1);

        Optional<Book> actual = bookRepository.findById("1");

        assertThat(expected).isEqualTo(actual.get());
    }

    @Test
    @DisplayName("Найти все книги")
    void shouldReturnAllBooks(){
        List<Book> actualBookList = bookRepository.findAll();
        assertThat(actualBookList)
                .extracting("title")
                .contains("book1", "book2", "book3");
    }
}

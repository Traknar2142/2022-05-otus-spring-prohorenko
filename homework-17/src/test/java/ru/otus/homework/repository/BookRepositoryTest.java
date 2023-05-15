package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.homework.PostgresTestContainerInit;
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
@DataJpaTest
public class BookRepositoryTest  extends PostgresTestContainerInit {
    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Найти книгу по id")
    void shouldReturnBookById(){
        Author author = new Author(1L, "author1");
        Genre genre = new Genre(1L, "genre1");
        Book expectedBook = new Book(1L, "book1", author, genre);

        Optional<Book> actual = bookRepository.getBookById(1L);

        assertThat(expectedBook).isEqualTo(actual.get());
    }

    @Test
    @DisplayName("Найти все книги")
    void shouldReturnAllBooks(){
        List<Book> actualBookList = bookRepository.getAll();
        assertThat(actualBookList)
                .extracting("title")
                .contains("book1", "book2", "book3", "book4");
    }
}

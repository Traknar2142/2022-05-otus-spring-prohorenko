package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Author;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Прохоренко Виктор
 */
@DisplayName("Дао авторов должно: ")
@DataJpaTest
public class AuthorRepositoryImplTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("Найти и вернуть сущность автора по его имени из базы")
    void shouldReturnAuthorByName(){
        Author expectedAuthor = new Author(1L, "author1");

        Optional<Author> actualAuthor = authorRepository.findByName("author1");
        assertThat(actualAuthor.get())
                .isNotNull()
                .isEqualTo(expectedAuthor);
    }
}

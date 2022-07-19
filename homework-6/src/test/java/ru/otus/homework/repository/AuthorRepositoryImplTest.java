package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Author;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Прохоренко Виктор
 */
@DisplayName("Дао авторов должно: ")
@DataJpaTest
@Import(AuthorRepositoryImpl.class)
public class AuthorRepositoryImplTest {
    @Autowired
    private AuthorRepositoryImpl authorRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Найти и вернуть сущность автора по его id из базы")
    void shouldReturnAuthorById(){
        Author expectedAuthor = new Author(1L, "author1");

        Optional<Author> actualAuthor = authorRepository.getAuthorById(1L);
        assertThat(actualAuthor.get())
                .isNotNull()
                .isEqualTo(expectedAuthor);
    }
    @Test
    @DisplayName("Сохранить в базу автора и вернуть сохраненного автора из базы")
    void shouldReturnSavedAuthor(){
        Author newAuthor = new Author("SomeWriter");
        Author expectedAuthor = new Author(5L, "SomeWriter");
        Author actualAuthor = authorRepository.saveAuthor(newAuthor);
        assertThat(actualAuthor)
                .isNotNull()
                .isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("Обновить сущность автора в базе")
    void shouldUpdateAuthor(){
        Author expectedAuthor = new Author(1L, "George Orwell2");
        Author actualAuthor = authorRepository.updateAuthor(expectedAuthor);
        assertThat(actualAuthor)
                .isNotNull()
                .isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("Удалить сущность из базы")
    void shouldDeleteAuthor(){
        Author authorToDelete = new Author(2L, "Raymond Douglas Bradbury");
        authorRepository.deleteAuthor(authorToDelete);
        assertNull(entityManager.find(Author.class, 2L));
    }
}

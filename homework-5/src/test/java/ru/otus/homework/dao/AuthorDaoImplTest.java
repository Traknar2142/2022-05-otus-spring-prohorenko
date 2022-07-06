package ru.otus.homework.dao;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Author;
import ru.otus.homework.exceptions.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Прохоренко Виктор
 */
@DisplayName("Дао авторов должно: ")
@JdbcTest
@Import(AuthorDaoImpl.class)
public class AuthorDaoImplTest {
    @Autowired
    private AuthorDaoImpl authorDao;

    @Test
    @DisplayName("Найти и вернуть сущность автора по его id из базы")
    void shouldReturnAuthorById(){
        Author expectedAuthor = new Author(1L, "author1");

        Author actualAuthor = authorDao.getAuthorById(1L);
        assertThat(actualAuthor)
                .isNotNull()
                .isEqualTo(expectedAuthor);
    }
    @Test
    @DisplayName("Сохранить в базу автора и вернуть сохраненного автора из базы")
    void shouldReturnSavedAuthor(){
        Author newAuthor = new Author("SomeWriter");
        Author expectedAuthor = new Author(5L, "SomeWriter");
        Author actualAuthor = authorDao.saveAuthor(newAuthor);
        assertThat(actualAuthor)
                .isNotNull()
                .isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("Обновить сущность автора в базе")
    void shouldUpdateAuthor(){
        Author expectedAuthor = new Author(1L, "George Orwell2");
        Author actualAuthor = authorDao.updateAuthor(expectedAuthor);
        assertThat(actualAuthor)
                .isNotNull()
                .isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("Удалить сущность из базы")
    void shouldDeleteAuthor(){
        Author authorToDelete = new Author(2L, "Raymond Douglas Bradbury");
        authorDao.deleteAuthor(authorToDelete);
        assertThrows(EntityNotFoundException.class, () -> authorDao.getAuthorById(authorToDelete.getId()));
    }
}

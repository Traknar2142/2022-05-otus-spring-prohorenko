package ru.otus.homework.dao;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Прохоренко Виктор
 */
@DisplayName("Дао жанров должно: ")
@JdbcTest
@Import(GenreDaoImpl.class)
public class GenreDaoImplTest {

    @Autowired
    private GenreDaoImpl genreDao;

    @Test
    @DisplayName("Найти и вернуть сущность жанра по его id из базы")
    void shouldReturnGenreById(){
        Genre expectedGenre = new Genre(1L, "genre1");

        Genre actualGenre = genreDao.getGenreById(1L);
        assertThat(actualGenre)
                .isNotNull()
                .isEqualTo(expectedGenre);
    }
    @Test
    @DisplayName("Сохранить в базу жанр и вернуть сохраненного жанр из базы")
    void shouldReturnSavedGenre(){
        Genre newGenre = new Genre("NewGenre");
        Genre expectedGenre = new Genre(5L, "NewGenre");
        Genre actualGenre = genreDao.saveGenre(newGenre);
        assertThat(actualGenre)
                .isNotNull()
                .isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("Обновить сущность жанра в базе")
    void shouldUpdateGenre(){
        Genre expectedGenre = new Genre(1L, "Dystopia2");
        Genre actualGenre = genreDao.updateGenre(expectedGenre);
        assertThat(actualGenre)
                .isNotNull()
                .isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("Удалить сущность из базы")
    void shouldDeleteGenre(){
        Genre genreToDelete = new Genre(2L, "Psychology");
        genreDao.deleteGenre(genreToDelete);
        assertThrows(EntityNotFoundException.class, () -> genreDao.getGenreById(genreToDelete.getId()));
    }
}

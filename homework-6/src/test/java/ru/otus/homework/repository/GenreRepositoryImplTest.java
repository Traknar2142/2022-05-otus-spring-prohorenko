package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.EntityNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Прохоренко Виктор
 */
@DisplayName("Дао жанров должно: ")
@DataJpaTest
@Import(GenreRepositoryImpl.class)
public class GenreRepositoryImplTest {
    @Autowired
    private GenreRepositoryImpl genreDao;

    @Test
    @DisplayName("Найти и вернуть сущность жанра по его id из базы")
    void shouldReturnGenreById(){
        Genre expectedGenre = new Genre(1L, "genre1");

        Optional<Genre> actualGenre = genreDao.getGenreById(1L);
        assertThat(actualGenre.get())
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
        assertThat(genreDao.getGenreById(genreToDelete.getId())).isEqualTo(Optional.empty());
    }
}

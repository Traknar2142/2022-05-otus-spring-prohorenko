package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.homework.domain.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Прохоренко Виктор
 */
@DisplayName("Репозиторий жанров жанров должен: ")
@DataJpaTest
public class GenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DisplayName("Найти и вернуть сущность жанра по названию из базы")
    void shouldReturnGenreByName(){
        Genre expectedGenre = new Genre(1L, "genre1");

        Optional<Genre> actualGenre = genreRepository.findByName("genre1");
        assertThat(actualGenre.get())
                .isNotNull()
                .isEqualTo(expectedGenre);
    }
}

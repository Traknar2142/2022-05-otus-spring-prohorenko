package ru.otus.homework.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.domain.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Прохоренко Виктор
 */
@DisplayName("Репозиторий жанров жанров должен: ")
@DataMongoTest
@ExtendWith(SpringExtension.class)
public class GenreRepositoryTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private GenreRepository genreRepository;

    @BeforeEach
    void init(){
        mongoTemplate.getDb().drop();
        Genre genre = new Genre("1", "genre1");
        mongoTemplate.save(genre, "genres");
    }

    @Test
    @DisplayName("Найти и вернуть сущность жанра по названию из базы")
    void shouldReturnGenreByName(){
        Genre expectedGenre = new Genre("1", "genre1");

        Optional<Genre> actualGenre = genreRepository.findByName("genre1");
        assertThat(actualGenre.get())
                .isNotNull()
                .isEqualTo(expectedGenre);
    }
}

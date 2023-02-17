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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Прохоренко Виктор
 */
@DisplayName("Репозиторий авторов должен: ")
@DataMongoTest
@ExtendWith(SpringExtension.class)
public class AuthorRepositoryTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void init(){
        Author author = new Author("1", "author1");
        mongoTemplate.save(author, "authors");
    }

    @Test
    @DisplayName("Найти и вернуть сущность автора по его имени из базы")
    void shouldReturnAuthorByName(){
        Author expectedAuthor = new Author("1", "author1");

        Optional<Author> actualAuthor = authorRepository.findByName("author1");
        assertThat(actualAuthor.get())
                .isNotNull()
                .isEqualTo(expectedAuthor);
    }
}

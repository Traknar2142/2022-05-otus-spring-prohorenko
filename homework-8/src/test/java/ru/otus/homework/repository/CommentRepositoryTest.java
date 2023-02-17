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
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Прохоренко Виктор
 */
@DisplayName("Репозиторий комментариев должен: ")
@DataMongoTest
@ExtendWith(SpringExtension.class)
public class CommentRepositoryTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    void init(){
        Author author = new Author("1", "author1");
        Genre genre = new Genre("1", "genre1");
        Book book = new Book("1", "book1", author, genre);
        mongoTemplate.save(book, "books");

        Comment comment1 = new Comment("comment1");
        comment1.setBook(book);
        comment1.setId("1");
        Comment comment2 = new Comment("comment2");
        comment2.setBook(book);
        comment2.setId("2");

        mongoTemplate.save(comment1, "comments");
        mongoTemplate.save(comment2, "comments");
    }

    @Test
    @DisplayName("Найти комментарии по id книги")
    void shouldReturnListOfCommentsByBookId(){
        List<Comment> actualCommentList = commentRepository.findCommentByBookId("1");
        assertThat(actualCommentList)
                .extracting("commentMessage")
                .contains("comment1", "comment2");
    }
}

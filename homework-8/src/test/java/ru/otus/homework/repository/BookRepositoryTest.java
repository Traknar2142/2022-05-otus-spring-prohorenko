package ru.otus.homework.repository;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Прохоренко Виктор
 */
@DisplayName("Репозиторий книг должен: ")
@DataMongoTest
@ExtendWith(SpringExtension.class)
public class BookRepositoryTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void init() {
        mongoTemplate.getDb().drop();
        Author author1 = new Author( "author1");
        mongoTemplate.save(author1, "authors");

        Genre genre1 = new Genre("genre1");
        mongoTemplate.save(genre1, "genres");

        Book expectedBook1 = new Book( "book1", author1, genre1);

        Author author2 = new Author( "author2");
        mongoTemplate.save(author2, "authors");

        Genre genre2 = new Genre("genre2");
        mongoTemplate.save(genre2, "genres");

        Book expectedBook2 = new Book( "book2", author2, genre2);

        Author author3 = new Author( "author3");
        mongoTemplate.save(author3, "authors");

        Genre genre3 = new Genre( "genre3");
        mongoTemplate.save(genre3, "genres");

        Book expectedBook3 = new Book( "book3", author3, genre3);

        mongoTemplate.save(expectedBook1, "books");
        mongoTemplate.save(expectedBook2, "books");
        mongoTemplate.save(expectedBook3, "books");

        Comment comment1 = new Comment("comment1");
        comment1.setBook(expectedBook3);
        Comment comment2 = new Comment("comment2");
        comment2.setBook(expectedBook3);

        mongoTemplate.save(comment1, "comments");
        mongoTemplate.save(comment2, "comments");
    }

    @Test
    @DisplayName("Найти книгу по id")
    void shouldReturnBookById() {
        Book expected = mongoTemplate.findOne(new Query(Criteria.where("title").is("book1")), Book.class);

        Optional<Book> actual = bookRepository.findById(expected.getId());

        assertThat(expected).isEqualTo(actual.get());
    }

    @Test
    @DisplayName("Найти все книги")
    void shouldReturnAllBooks() {
        List<Book> actualBookList = bookRepository.findAll();
        assertThat(actualBookList)
                .extracting("title")
                .contains("book1", "book2", "book3");
    }


    @Test
    @DisplayName("Удалить книгу и все коментарии к ней")
    void shouldDeleteBookAndComments() {
        Book deletableBook = mongoTemplate.findOne(new Query(Criteria.where("title").is("book3")), Book.class);

        bookRepository.deleteByIdCustom(deletableBook.getId());
        List<Book> books = mongoTemplate.find(new Query(Criteria.where("_id").is(deletableBook.getId())), Book.class);
        List<Comment> comments = mongoTemplate.find(new Query(Criteria.where("book.$id").is(new ObjectId(deletableBook.getId()))), Comment.class);
        assertThat(comments).isEmpty();
        assertThat(books).isEmpty();
    }
}

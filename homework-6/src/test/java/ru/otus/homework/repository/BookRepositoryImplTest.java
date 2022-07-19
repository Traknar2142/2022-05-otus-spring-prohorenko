package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.EntityNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Дао книг должно: ")
@DataJpaTest
@Import({BookRepositoryImpl.class})
public class BookRepositoryImplTest {
    @Autowired
    private BookRepositoryImpl bookDao;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Найти и вернуть книгу по его id из базы")
    void shouldReturnBookById(){
        Author author = new Author(1L, "author1");
        Genre genre = new Genre(1L, "genre1");
        List<Comment> comments = Arrays.asList(new Comment(1L, "comment-1-1"), new Comment(2L, "comment-1-2"));
        Book expectedBook = new Book(1L, "book1", author, genre);

        Optional<Book> actualBook = bookDao.getBookById(1L);
        assertThat(expectedBook)
                .isNotNull()
                .isEqualTo(actualBook.get());
    }
    @Test
    @DisplayName("Сохранить в базу книгу и вернуть сохраненную книгу из базы")
    void shouldReturnSavedBook(){
        Author authorForSave = new Author("author1");
        Genre genreForSave = new Genre("genre1");
        List<Comment> commentsForSave = Arrays.asList(new Comment("comment-5-1"), new Comment("comment-5-2"));
        Book bookForSave = new Book("1984-2", authorForSave, genreForSave);

        Book actualBook = bookDao.saveBook(bookForSave);

        assertThat(actualBook.getId()).isEqualTo(5L);
        assertThat(actualBook.getTitle()).isEqualTo("1984-2");
        assertThat(actualBook.getAuthor()).isEqualTo(authorForSave);
        assertThat(actualBook.getGenre()).isEqualTo(genreForSave);
    }

    @Test
    @DisplayName("Обновить сущность книги в базе")
    void shouldUpdateBook(){
        Author authorForUpdate = new Author(1L, "author1");
        Genre genreForUpdate = new Genre(1L, "genre1");
        List<Comment> comments = Arrays.asList(new Comment(1L, "comment-1-1"), new Comment(2L, "comment-1-2"));
        Book bookForUpdate = new Book(1L, "updateTitle", authorForUpdate, genreForUpdate);

        Book actualBook = bookDao.updateBook(bookForUpdate);

        assertThat(actualBook.getId()).isEqualTo(1L);
        assertThat(actualBook.getTitle()).isEqualTo("updateTitle");
        assertThat(actualBook.getAuthor()).isEqualTo(authorForUpdate);
        assertThat(actualBook.getGenre()).isEqualTo(genreForUpdate);
    }

    @Test
    @DisplayName("Удалить сущность из базы")
    void shouldDeleteBook(){
        bookDao.deleteBookById(3L);
        assertNull(entityManager.find(Book.class, 3L));
    }

    @Test
    @DisplayName("Вернуть список всех книг")
    void shouldReturnListOfBooks(){
        List<Book> books = bookDao.getAll();
        assertThat(books).isNotEmpty();
    }
}

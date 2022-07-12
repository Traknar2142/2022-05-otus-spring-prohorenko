package ru.otus.homework.dao;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.EntityNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Дао книг должно: ")
@JdbcTest
@Import({BookDaoImpl.class})
public class BookDaoImplTest {
    @Autowired
    private BookDaoImpl bookDao;

    @Test
    @DisplayName("Найти и вернуть книгу по его id из базы")
    void shouldReturnBookById(){
        Author author = new Author(1L, "author1");
        Genre genre = new Genre(1L, "genre1");
        Book expectedBook = new Book(1L, "book1", author, genre);

        Book actualBook = bookDao.getBookById(1L);
        assertThat(expectedBook)
                .isNotNull()
                .isEqualTo(actualBook);
    }
    @Test
    @DisplayName("Сохранить в базу книгу и вернуть сохраненную книгу из базы")
    void shouldReturnSavedBook(){
        Author authorForSave = new Author(1L,"author1");
        Genre genreForSave = new Genre(1L,"genre1");
        Book bookForSave = new Book(5L,"1984-2", authorForSave, genreForSave);

        Author author = new Author(1L, "author1");
        Genre genre = new Genre(1L,"genre1");
        Book expectedBook = new Book(5L, "1984-2", author, genre);

        Book actualBook = bookDao.saveBook(bookForSave);
        assertThat(actualBook)
                .isNotNull()
                .isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("Обновить сущность книги в базе")
    void shouldUpdateBook(){
        Author authorForUpdate = new Author(1L, "author1");
        Genre genreForUpdate = new Genre(1L, "genre1");
        Book bookForUpdate = new Book(1L, "updateTitle", authorForUpdate, genreForUpdate);
        Book actualBook = bookDao.updateBook(bookForUpdate);
        assertThat(actualBook)
                .isNotNull()
                .isEqualTo(bookForUpdate);
    }

    @Test
    @DisplayName("Удалить сущность из базы")
    void shouldDeleteBook(){
        bookDao.deleteBookById(3L);
        assertThrows(EntityNotFoundException.class, () -> bookDao.getBookById(3L));
    }

    @Test
    @DisplayName("Вернуть список всех книг")
    void shouldReturnListOfBooks(){
        List<Book> books = bookDao.getAll();
        assertThat(books).isNotEmpty();
    }
}

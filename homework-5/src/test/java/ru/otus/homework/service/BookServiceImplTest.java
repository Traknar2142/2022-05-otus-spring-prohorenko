package ru.otus.homework.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.dao.GenreDao;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * @author Прохоренко Виктор
 */
@DisplayName("Сервис книг должен")
@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    @Mock
    private OutRenderService<Book> bookRenderService;
    @Mock
    private BookDao bookDao;
    @Mock
    private AuthorDao authorDao;
    @Mock
    private GenreDao genreDao;
    @InjectMocks
    private BookServiceImpl bookService;

    @DisplayName("Должен вернуть сохраненную книгу")
    @SneakyThrows
    @Test
    void shouldReturnSavedBook() {
        Author authorForSave = new Author(5L, "George Orwell2");
        Genre genreForSave = new Genre(5L, "Dystopia2");
        Book bookForSave = new Book(5L, "1984-2", authorForSave, genreForSave);

        Mockito.when(genreDao.getGenreByName("Dystopia2")).thenReturn(genreForSave);
        Mockito.when(authorDao.getAuthorByName("George Orwell2")).thenReturn(authorForSave);
        Mockito.when(bookDao.saveBook(bookForSave)).thenReturn(bookForSave);

        Book actual = bookService.addBook(bookForSave);
        assertThat(bookForSave).isEqualTo(actual);
    }

    @DisplayName("Должен вернуть кингу по id")
    @SneakyThrows
    @Test
    void shouldReturnBookById() {
        Author authorForSave = new Author(5L, "George Orwell2");
        Genre genreForSave = new Genre(5L, "Dystopia2");
        Book bookForSave = new Book(5L, "1984-2", authorForSave, genreForSave);

        Mockito.when(bookDao.getBookById(5L)).thenReturn(bookForSave);

        Book actual = bookService.getById(5L);

        assertThat(bookForSave).isEqualTo(actual);
    }

    @DisplayName("Должен вернуть обновленную запись о книге")
    @SneakyThrows
    @Test
    void shouldReturnUpdatedBook() {
        Author authorForSave = new Author(5L, "George Orwell2");
        Genre genreForSave = new Genre(5L, "Dystopia2");
        Book bookForSave = new Book(5L, "1984-2", authorForSave, genreForSave);

        Mockito.when(genreDao.getGenreByName("Dystopia2")).thenReturn(genreForSave);
        Mockito.when(authorDao.getAuthorByName("George Orwell2")).thenReturn(authorForSave);
        Mockito.when(bookDao.updateBook(bookForSave)).thenReturn(bookForSave);

        Book actual = bookService.updateBook(bookForSave);
        assertThat(bookForSave).isEqualTo(actual);
    }
}

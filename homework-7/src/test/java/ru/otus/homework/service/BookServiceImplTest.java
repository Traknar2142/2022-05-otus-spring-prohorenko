package ru.otus.homework.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.repository.AuthorRepository;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.repository.GenreRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Прохоренко Виктор
 */
@DisplayName("Сервис книг должен")
@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    @Mock
    private BookRenderService bookRenderService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private GenreRepository genreRepository;
    @InjectMocks
    private BookServiceImpl bookService;

    @DisplayName("Должен вернуть сохраненную книгу")
    @Test
    void shouldReturnSavedBook() {
        Author authorForSave = new Author(5L, "George Orwell2");
        Genre genreForSave = new Genre(5L, "Dystopia2");
        Book bookForSave = new Book(5L, "1984-2", authorForSave, genreForSave);

        Mockito.when(genreRepository.findByName("Dystopia2")).thenReturn(Optional.of(genreForSave));
        Mockito.when(authorRepository.findByName("George Orwell2")).thenReturn(Optional.of(authorForSave));
        Mockito.when(bookRepository.save(bookForSave)).thenReturn(bookForSave);

        Book actual = bookService.addBook(bookForSave);
        assertThat(bookForSave).isEqualTo(actual);
    }

    @DisplayName("Должен вернуть кингу по id")
    @Test
    void shouldReturnBookById() {
        Author authorForSave = new Author(5L, "George Orwell2");
        Genre genreForSave = new Genre(5L, "Dystopia2");
        Book bookForSave = new Book(5L, "1984-2", authorForSave, genreForSave);

        Mockito.when(bookRepository.findById(5L)).thenReturn(Optional.of(bookForSave));

        Book actual = bookService.getById(5L);

        assertThat(bookForSave).isEqualTo(actual);
    }

    @DisplayName("Должен вернуть обновленную запись о книге")
    @Test
    void shouldReturnUpdatedBook() {
        Author authorForSave = new Author(5L, "George Orwell2");
        Genre genreForSave = new Genre(5L, "Dystopia2");
        Book bookForSave = new Book(5L, "1984-2", authorForSave, genreForSave);

        Mockito.when(genreRepository.findByName("Dystopia2")).thenReturn(Optional.of(genreForSave));
        Mockito.when(authorRepository.findByName("George Orwell2")).thenReturn(Optional.of(authorForSave));
        Mockito.when(bookRepository.save(bookForSave)).thenReturn(bookForSave);

        Book actual = bookService.updateBook(bookForSave);
        assertThat(bookForSave).isEqualTo(actual);
    }
}

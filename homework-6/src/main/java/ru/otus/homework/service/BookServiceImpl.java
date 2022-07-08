package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.repository.AuthorRepository;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.repository.GenreRepository;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.EntityNotFoundException;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

/**
 * @author Прохоренко Виктор
 */
@Service
public class BookServiceImpl implements BookService {
    private final OutRenderService<Book> bookRenderService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookServiceImpl(OutRenderService<Book> bookRenderService, BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRenderService = bookRenderService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public void printAllBooks() {
        List<Book> books = bookRepository.getAll();
        bookRenderService.printFormatMessage(books);
    }

    @Override
    public void printBookById(Long id) {
        Optional<Book> bookById = bookRepository.getBookById(id);
        bookRenderService.printFormatMessage(List.of(bookById.get()));
    }


    @Override
    public Book addBook(Book book) {
        updateGenreForBook(book);
        updateAuthorForBook(book);
        return bookRepository.saveBook(book);
    }

    @Override
    public Book getById(Long id) {
        return bookRepository.getBookById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Запись о книге с id {0} не найдена", id)));
    }

    @Override
    public Book updateBook(Book book) {
        updateGenreForBook(book);
        updateAuthorForBook(book);
        return bookRepository.updateBook(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteBookById(id);
    }

    private Genre getOrSaveGenre(String name) {
        return genreRepository.getGenreByName(name).orElse(genreRepository.saveGenre(new Genre(name)));
    }

    private void updateGenreForBook(Book book) {
        if (book.getGenre() != null) {
            Genre genreByName = getOrSaveGenre(book.getGenre().getName());
            book.setGenre(genreByName);
        }
    }

    private void updateAuthorForBook(Book book) {
        if (book.getAuthor() != null) {
            Author authorByName = getOrSaveAuthor(book.getAuthor().getName());
            book.setAuthor(authorByName);
        }
    }

    private Author getOrSaveAuthor(String name) {
        return authorRepository.getAuthorByName(name).orElse(authorRepository.saveAuthor(new Author(name)));
    }
}

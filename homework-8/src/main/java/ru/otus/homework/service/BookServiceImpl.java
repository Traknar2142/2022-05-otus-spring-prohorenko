package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.EntityNotFoundException;
import ru.otus.homework.repository.AuthorRepository;
import ru.otus.homework.repository.BookRepository;
import ru.otus.homework.repository.CommentRepository;
import ru.otus.homework.repository.GenreRepository;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

/**
 * @author Прохоренко Виктор
 */
@Service
public class BookServiceImpl implements BookService {
    private final BookRenderService bookRenderService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookServiceImpl(BookRenderService bookRenderService, BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepositoryDao, CommentRepository commentRepository) {
        this.bookRenderService = bookRenderService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepositoryDao;
    }

    @Override
    public void printAllBooks() {
        List<Book> books = bookRepository.findAll();
        bookRenderService.printFormatMessage(books);
    }

    @Override
    public void printBookById(String id) {
        Book bookById = getById(id);
        bookRenderService.printFormatMessage(List.of(bookById));
    }

    @Override
    public Book getById(String id) {
        return bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Запись о книге с id {0} не найдена", id)));
    }

    @Override
    public Book addBook(Book book) {
        updateGenreForBook(book);
        updateAuthorForBook(book);
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        updateGenreForBook(book);
        updateAuthorForBook(book);
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(String id) {
        bookRepository.deleteByIdCustom(id);
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

    private Genre getOrSaveGenre(String name) {
        Optional<Genre> genreByName = genreRepository.findByName(name);
        if (genreByName.isEmpty()){
            return genreRepository.save(new Genre(name));
        }
        return genreByName.get();
    }

    private Author getOrSaveAuthor(String name) {
        Optional<Author> authorByName = authorRepository.findByName(name);
        if (authorByName.isEmpty()){
            return authorRepository.save(new Author(name));
        }
        return authorByName.get();
    }

}

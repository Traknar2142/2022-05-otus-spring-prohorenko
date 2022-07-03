package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.dao.GenreDao;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.EntityNotFoundInDbException;

import java.util.List;

/**
 * @author Прохоренко Виктор
 */
@Service
public class BookServiceImpl implements BookService {
    private final OutRenderService<Book> bookRenderService;
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    public BookServiceImpl(OutRenderService<Book> bookRenderService, BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookRenderService = bookRenderService;
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public void printAllBooks() {
        List<Book> books = bookDao.getAll();
        bookRenderService.printFormatMessage(books);
    }

    @Override
    public Book addBook(Book book) throws EntityNotFoundInDbException {
        updateGenreForBook(book);
        updateAuthorForBook(book);
        return bookDao.saveBook(book);
    }

    @Override
    public Book getById(Long id) throws EntityNotFoundInDbException {
        return bookDao.getBookById(id);
    }

    @Override
    public Book updateBook(Book book) throws EntityNotFoundInDbException {
        updateGenreForBook(book);
        updateAuthorForBook(book);
        return bookDao.updateBook(book);
    }

    @Override
    public void deleteBook(Long id) throws EntityNotFoundInDbException {
        bookDao.deleteBookById(id);
    }

    private Genre getOrSaveGenre(String name) throws EntityNotFoundInDbException {
        Genre genreByName = null;
        try {
            genreByName = genreDao.getGenreByName(name);
        } catch (EntityNotFoundInDbException e) {
            genreByName = genreDao.saveGenre(new Genre(name));
            return genreByName;
        }
        return genreByName;
    }

    private void updateGenreForBook(Book book) throws EntityNotFoundInDbException {
        if (book.getGenre() != null) {
            Genre genreByName = getOrSaveGenre(book.getGenre().getName());
            book.setGenre(genreByName);
        }
    }

    private void updateAuthorForBook(Book book) throws EntityNotFoundInDbException {
        if (book.getAuthor() != null) {
            Author authorByName = getOrSaveAuthor(book.getAuthor().getName());
            book.setAuthor(authorByName);
        }
    }

    private Author getOrSaveAuthor(String name) throws EntityNotFoundInDbException {
        Author authorByName = null;
        try {
            authorByName = authorDao.getAuthorByName(name);
        } catch (EntityNotFoundInDbException e) {
            authorByName = authorDao.saveAuthor(new Author(name));
            return authorByName;
        }
        return authorByName;
    }
}

package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.dao.AuthorDao;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.dao.GenreDao;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.EntityNotFoundException;

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
    public void printBookById(Long id) {
        Book bookById = bookDao.getBookById(id);
        bookRenderService.printFormatMessage(List.of(bookById));
    }


    @Override
    public Book addBook(Book book) {
        updateGenreForBook(book);
        updateAuthorForBook(book);
        return bookDao.saveBook(book);
    }

    @Override
    public Book getById(Long id) {
        return bookDao.getBookById(id);
    }

    @Override
    public Book updateBook(Book book) {
        updateGenreForBook(book);
        updateAuthorForBook(book);
        return bookDao.updateBook(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookDao.deleteBookById(id);
    }

    private Genre getOrSaveGenre(String name) {
        Genre genreByName = null;
        try {
            genreByName = genreDao.getGenreByName(name);
        } catch (EntityNotFoundException e) {
            genreByName = genreDao.saveGenre(new Genre(name));
            return genreByName;
        }
        return genreByName;
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
        Author authorByName = null;
        try {
            authorByName = authorDao.getAuthorByName(name);
        } catch (EntityNotFoundException e) {
            authorByName = authorDao.saveAuthor(new Author(name));
            return authorByName;
        }
        return authorByName;
    }
}

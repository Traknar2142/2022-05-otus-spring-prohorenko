package ru.otus.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.domain.Author;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Genre;
import ru.otus.homework.exceptions.EntityNotFoundInDbException;
import ru.otus.homework.service.BookService;

/**
 * @author Прохоренко Виктор
 */
@ShellComponent
@RequiredArgsConstructor
public class ShellController {
    private final BookService bookService;
    private Book book;

    @ShellMethod(value = "printBooks", key = {"printBooks", "pb"})
    public void printAllBooks() {
        bookService.printAllBooks();
    }

    @ShellMethod(value = "addBook", key = {"addBook", "ab"})
    public String addBook() throws EntityNotFoundInDbException {
        if (book != null) {
            bookService.addBook(book);
            book=null;
            return String.format("Книга успешно добавлена в базу: %s", book);
        } else {
            return "Книга не добавлена т.к. запись о книге пуста";
        }
    }

    @ShellMethod(value = "createBookRecord", key = {"createBookRecord", "cbr"})
    public String createBookRecord(@ShellOption String title) {
        book = new Book(title);
        return String.format("Запись о книге успешно создана: %s", book);
    }

    @ShellMethod(value = "updateGenreRecord", key = {"updateGenreRecord", "ugr"})
    public String updateGenreRecord(@ShellOption String genreName) {
        if (book != null) {
            Genre genre = new Genre(genreName);
            book.setGenre(genre);
            return String.format("Успешно обновлен жанр книги: %s", book);
        } else {
            return "Сначала создайте запись книги";
        }
    }

    @ShellMethod(value = "updateAuthorRecord", key = {"updateAuthorRecord", "uar"})
    public String updateAuthorRecord(@ShellOption String authorName) {
        if (book != null) {
            Author author = new Author(authorName);
            book.setAuthor(author);
            return String.format("Успешно обновлен автор книги: %s", book);
        } else {
            return "Сначала создайте запись книги";
        }
    }

    @ShellMethod(value = "downloadForUpdate", key = {"downloadForUpdate", "dfu"})
    public String downloadForUpdate(@ShellOption Long id) throws EntityNotFoundInDbException {
        book = bookService.getById(id);
        return String.format("Успешно загружена запись для обновления: %s", book);
    }

    @ShellMethod(value = "updateBook", key = {"updateBook", "ub"})
    public String updateBook() throws EntityNotFoundInDbException {
        if (book != null) {
            bookService.updateBook(book);
            book=null;
            return String.format("Книга успешно обновлена в базе: %s", book);
        } else {
            return "Книга не обновлена т.к. запись о книге пуста";
        }
    }

    @ShellMethod(value = "deleteBook", key = {"deleteBook", "db"})
    public String deleteBook(@ShellOption Long id) throws EntityNotFoundInDbException {
        bookService.deleteBook(id);
        return "Книга успешно удалена";
    }

    @ShellMethod(value = "printCurrentBookRecord", key = {"printCurrentBookRecord", "pcbr"})
    public String printCurrentBookRecord() {
        if (book != null) {
            return String.format("Текущая запись о книге: %s", book);
        } else return "запись о книге не создана";
    }

}

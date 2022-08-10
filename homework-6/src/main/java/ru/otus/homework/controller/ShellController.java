package ru.otus.homework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.CommentProcessor;
import ru.otus.homework.service.CommentService;
import ru.otus.homework.service.BookProcessor;

/**
 * @author Прохоренко Виктор
 */
@ShellComponent
@RequiredArgsConstructor
public class ShellController {
    private final BookService bookService;
    private final BookProcessor bookProcessor;
    private final CommentService commentService;
    private final CommentProcessor commentProcessor;

    @ShellMethod(value = "printBooks", key = {"printBooks", "pbs"})
    public void printAllBooks() {
        bookService.printAllBooks();
    }

    @ShellMethod(value = "printBookById", key = {"printBookById", "pbbi"})
    public void printBookById(@ShellOption Long id) {
        bookService.printBookById(id);
    }

    @ShellMethod(value = "addBook", key = {"addBook", "ab"})
    public String addBook() {
        Book book = bookProcessor.addProcess();
        return String.format("Книга успешно добавлена: %s", book);
    }

    @ShellMethod(value = "updateBook", key = {"updateBook", "ub"})
    public String updateBook() {
        Book book = bookProcessor.updateProcess();
        return String.format("Книга успешно обновлена: %s", book);

    }

    @ShellMethod(value = "deleteBook", key = {"deleteBook", "db"})
    public String deleteBook(@ShellOption Long id) {
        bookService.deleteBook(id);
        return "Книга успешно удалена";
    }

    @ShellMethod(value = "addComment", key = {"addComment", "ac"})
    public String addComment(){
        Comment comment = commentProcessor.addProcess();
        return String.format("Комментарий успешно добавлен успешно добавлена: %s", comment);
    }

    @ShellMethod(value = "updateComment", key = {"updateComment", "uc"})
    public String updateComment(){
        Comment comment = commentProcessor.updateProcess();
        return String.format("Комментарий успешно обновлен: %s", comment);
    }

    @ShellMethod(value = "deleteComment", key = {"deleteComment", "dc"})
    public String deleteComment(@ShellOption Long id){
        commentService.deleteComment(id);
        return "Комментарий успешно удален";
    }

    @ShellMethod(value = "getCommentsByBookId", key = {"getCommentsByBookId", "gcbbi"})
    public void getCommentsByBookId(@ShellOption Long bookId){
        commentService.printCommentsByBooksId(bookId);
    }
}

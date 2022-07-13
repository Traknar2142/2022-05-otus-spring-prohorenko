package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Book;
import ru.otus.homework.transformer.BookTransformer;

/**
 * @author Прохоренко Виктор
 */
@Service
public class BookProcessorImpl implements EntityProcessor<Book> {
    private final MessageDialogService messageDialogService;
    private final BookService bookService;

    public BookProcessorImpl(MessageDialogService messageDialogService, BookService bookService) {
        this.messageDialogService = messageDialogService;
        this.bookService = bookService;
    }

    @Override
    public Book addProcess() {
        messageDialogService.outputMessage("Введите название книги");
        String title = messageDialogService.inputMessage();
        messageDialogService.outputMessage("Введите название жанра");
        String genreName = messageDialogService.inputMessage();
        messageDialogService.outputMessage("Введите автора");
        String authorName = messageDialogService.inputMessage();

        Book book = BookTransformer.transformForAdd(title, authorName, genreName);

        return bookService.addBook(book);
    }

    @Override
    public Book updateProcess() {
        messageDialogService.outputMessage("Введите id книги для изменения");
        Long id = Long.valueOf(messageDialogService.inputMessage());
        Book book = bookService.getById(id);

        messageDialogService.outputMessage("Введите название книги");
        String title = messageDialogService.inputMessage();
        messageDialogService.outputMessage("Введите название жанра");
        String genreName = messageDialogService.inputMessage();
        messageDialogService.outputMessage("Введите автора");
        String authorName = messageDialogService.inputMessage();

        BookTransformer.transformForUpdate(book, title, authorName, genreName);

        return bookService.updateBook(book);
    }

}

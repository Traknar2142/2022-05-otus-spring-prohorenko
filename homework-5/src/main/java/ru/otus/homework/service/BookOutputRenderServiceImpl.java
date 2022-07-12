package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Book;

import java.util.List;

/**
 * @author Прохоренко Виктор
 */
@Service
public class BookOutputRenderServiceImpl implements OutRenderService<Book>{
    private final MessageDialogService messageDialogService;

    public BookOutputRenderServiceImpl(MessageDialogService messageDialogService) {
        this.messageDialogService = messageDialogService;
    }

    @Override
    public void printFormatMessage(List<Book> books) {
        messageDialogService.outputMessage(formatListToStringForOutput(books));
    }
    private String formatListToStringForOutput(List<Book> books) {
        StringBuilder builder = new StringBuilder();
        for (Book book : books){
            builder.append(book.toString() + "\n");
        }
        return builder.toString();
    }
}

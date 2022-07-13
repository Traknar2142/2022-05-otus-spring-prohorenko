package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.exceptions.EntityNotFoundException;

import java.util.List;

/**
 * @author Прохоренко Виктор
 */
@Service
public class CommentProcessorImpl implements EntityProcessor<Comment>{
    private final MessageDialogService messageDialogService;
    private final CommentService commentService;
    private final BookService bookService;

    public CommentProcessorImpl(MessageDialogService messageDialogService, CommentService commentService, BookService bookService) {
        this.messageDialogService = messageDialogService;
        this.commentService = commentService;
        this.bookService = bookService;
    }

    @Transactional
    @Override
    public Comment addProcess() {
        messageDialogService.outputMessage("Введите id книги для комментирования");
        Long id = Long.valueOf(messageDialogService.inputMessage());
        Book book = bookService.getById(id);
        messageDialogService.outputMessage("Введите комментарий");
        String commentMessage = messageDialogService.inputMessage();
        List<Comment> comments = book.getComments();//.add(new Comment(commentMessage));
        comments.add(new Comment(commentMessage));
        Book updateBook = bookService.updateBook(book);
        return updateBook.getComments()
                .stream()
                .filter(com -> com.getCommentMessage().equals(commentMessage))
                .findFirst()
                .orElseThrow(() ->new EntityNotFoundException("Комментарий не найден"));
    }

    @Transactional
    @Override
    public Comment updateProcess() {
        messageDialogService.outputMessage("Введите id комментария для изменения");
        Long id = Long.valueOf(messageDialogService.inputMessage());
        Comment commentById = commentService.getCommentById(id);

        messageDialogService.outputMessage("Введите новый комментарий");
        String commentMessage = messageDialogService.inputMessage();

        commentById.setCommentMessage(commentMessage);
        return commentService.updateComment(commentById);
    }
}

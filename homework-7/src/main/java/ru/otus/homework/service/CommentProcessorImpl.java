package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;

/**
 * @author Прохоренко Виктор
 */
@Service
public class CommentProcessorImpl implements CommentProcessor {
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
        Comment comment = new Comment(commentMessage);
        comment.setBook(book);
        return commentService.addComment(comment);
    }

    @Override
    public Comment updateProcess() {
        messageDialogService.outputMessage("Введите id комментария для изменения");
        Long id = Long.valueOf(messageDialogService.inputMessage());

        messageDialogService.outputMessage("Введите новый комментарий");
        String commentMessage = messageDialogService.inputMessage();

        return updateProcess2(id, commentMessage);
    }

    @Transactional
    public Comment updateProcess2(Long id, String message){
        Comment commentById = commentService.getCommentById(id);
        commentById.setCommentMessage(message);
        return commentService.addComment(commentById);
    }
}

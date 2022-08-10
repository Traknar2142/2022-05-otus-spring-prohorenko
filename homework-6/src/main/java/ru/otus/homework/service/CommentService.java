package ru.otus.homework.service;

import ru.otus.homework.domain.Comment;

import java.util.List;

/**
 * @author Прохоренко Виктор
 */
public interface CommentService {
    void printCommentsByBooksId(Long bookId);
    Comment addComment(Comment comment);
    List<Comment> getCommentsByBookId(Long bookId);
    Comment getCommentById(Long id);
    Comment updateComment(Comment comment);
    void deleteComment(Long id);

}

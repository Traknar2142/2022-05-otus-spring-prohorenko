package ru.otus.homework.service;

import ru.otus.homework.domain.Comment;

import java.util.List;

/**
 * @author Прохоренко Виктор
 */
public interface CommentService {
    void printCommentsByBooksId(String bookId);
    Comment addComment(Comment comment);
    List<Comment> getCommentsByBookId(String bookId);
    Comment getCommentById(String id);
    Comment updateComment(Comment comment);
    void deleteComment(String id);

}

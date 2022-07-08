package ru.otus.homework.repository;

import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;

import java.util.List;
import java.util.Optional;

/**
 * @author Прохоренко Виктор
 */
public interface CommentRepository {
    Comment saveComment(Comment comment);
    Optional<Comment> getCommentById(Long id);
    List<Comment> getAll();
    Comment updateComment(Comment comment);
    void deleteCommentById(Long id);
}

package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.domain.Comment;

import java.util.List;
import java.util.Optional;

/**
 * @author Прохоренко Виктор
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentByBookId(Long bookId);
}

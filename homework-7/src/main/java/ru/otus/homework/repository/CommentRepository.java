package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.homework.domain.Comment;

import java.util.List;
import java.util.Optional;

/**
 * @author Прохоренко Виктор
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c join fetch c.book where c.book.id = :bookId")
    List<Comment> findCommentByBookId(Long bookId);
}

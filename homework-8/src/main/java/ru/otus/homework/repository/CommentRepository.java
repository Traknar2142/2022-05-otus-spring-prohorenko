package ru.otus.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.homework.domain.Comment;

import java.util.List;

/**
 * @author Прохоренко Виктор
 */
public interface CommentRepository extends MongoRepository<Comment, String> {
    @Query(value = "{'book.id' : :#{#bookId}}")
    List<Comment> findCommentByBookId(@Param("bookId") String bookId);
}

package ru.otus.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.homework.domain.Comment;

import java.util.List;

/**
 * @author Прохоренко Виктор
 */
public interface CommentRepository extends MongoRepository<Comment, String> {
    @Query(value = "{'book.id' : ?0}")
    List<Comment> findCommentByBookId(String bookId);
}

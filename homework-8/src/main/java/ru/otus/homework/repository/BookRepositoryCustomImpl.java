package ru.otus.homework.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.homework.domain.Book;
import ru.otus.homework.domain.Comment;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;


public class BookRepositoryCustomImpl implements BookRepositoryCustom{
    private final MongoTemplate mongoTemplate;

    public BookRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void deleteByIdCustom(String bookId) {
        removeOrphanComments(bookId);
        mongoTemplate.remove(new Query(where("_id").is(bookId)), Book.class);
    }

    private void removeOrphanComments(String id){
        List<Comment> comments = mongoTemplate.find(new Query(Criteria.where("book.$id").is(new ObjectId(id))), Comment.class);
        List<String> commentIds = comments
                .stream()
                .map(Comment::getId)
                .collect(Collectors.toList());

        commentIds.forEach(commentId -> mongoTemplate.remove(new Query(where("_id").is(commentId)), Comment.class));
    }
}

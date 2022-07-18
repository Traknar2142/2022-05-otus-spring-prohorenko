package ru.otus.homework.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.domain.Comment;
import ru.otus.homework.exceptions.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

/**
 * @author Прохоренко Виктор
 */
@Repository
public class CommentRepositoryImpl implements CommentRepository{

    @PersistenceContext
    private final EntityManager entityManager;

    public CommentRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Comment saveComment(Comment comment) {
        if (comment.getId() == null){
            entityManager.persist(comment);
            return comment;
        }
        return entityManager.merge(comment);
    }

    @Override
    public Optional<Comment> getCommentById(Long id) {
        return Optional.ofNullable(entityManager.find(Comment.class, id));
    }

    @Override
    public List<Comment> getCommentsByBookId(Long bookId) {
        TypedQuery<Comment> query = entityManager.createQuery("select c from Comment c where c.book.id = :bookId", Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Override
    public List<Comment> getAll() {
        TypedQuery<Comment> query = entityManager.createQuery("select c from Comment c join fetch c.book", Comment.class);
        return query.getResultList();
    }

    @Override
    public Comment updateComment(Comment comment) {
        Long id = comment.getId();
        String commentMessage = comment.getCommentMessage();
        Query query = entityManager.createQuery("update Comment  c " +
                "set c.commentMessage = :commentMessage " +
                "where c.id = :id");
        query.setParameter("commentMessage", commentMessage);
        query.setParameter("id", id);
        query.executeUpdate();
        return getCommentById(id).orElseThrow(()-> new EntityNotFoundException(MessageFormat.format("Запись о комментарии {0} не найдена", comment)));
    }

    @Override
    public void deleteCommentById(Long id) {
        Query query = entityManager.createQuery("delete from Comment  c where c.id = :id");
        query.setParameter("id", id);
        int i = query.executeUpdate();
        if (i==0) throw new EntityNotFoundException(MessageFormat.format("Запись о комментарии {0} не найдена", id));
    }
}

package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.domain.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Прохоренко Виктор
 */
@DisplayName("Дао комментариев должно: ")
@DataJpaTest
@Import({CommentRepositoryImpl.class})
public class CommentRepositoryImplTest {
    @Autowired
    private CommentRepositoryImpl commentRepository;

    @Test
    @DisplayName("Найти и вернуть сомментарий по его id из базы")
    void shouldReturnCommentById(){
        Comment expectedComment = new Comment(1L, "comment-1-1");
        Comment actualComment = commentRepository.getCommentById(1L).get();
        assertThat(expectedComment).isEqualTo(actualComment);
    }
    @Test
    @DisplayName("Сохранить в базу сомментарий и вернуть сохраненный сомментарий из базы")
    void shouldReturnSavedComment(){
        Comment expectedComment = new Comment("comment-5-1");
        Comment actualComment = commentRepository.saveComment(expectedComment);
        assertThat(expectedComment).isEqualTo(actualComment);
    }

    @Test
    @DisplayName("Обновить комментария книги в базе")
    void shouldUpdateComment(){
        Comment expectedComment = new Comment(1L, "comment-1-9999");
        Comment actualComment = commentRepository.updateComment(expectedComment);
        assertThat(expectedComment).isEqualTo(actualComment);
    }

    @Test
    @DisplayName("Удалить комментария из базы")
    void shouldDeleteComment(){
        commentRepository.deleteCommentById(2L);
        assertThat(commentRepository.getCommentById(2L)).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("Вернуть список всех комментариев")
    void shouldReturnListOfComment(){
        List<Comment> comments = commentRepository.getAll();
        assertThat(comments).isNotEmpty();
    }
}

package ru.otus.homework.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.homework.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Прохоренко Виктор
 */
@DisplayName("Репозиторий комментариев должен: ")
@DataJpaTest
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("Найти комментарии по id книги")
    void shouldReturnListOfCommentsByBookId(){
        List<Comment> actualCommentList = commentRepository.findCommentByBookId(1L);
        assertThat(actualCommentList)
                .extracting("commentMessage")
                .contains("comment-1-1", "comment-1-2");
    }
}

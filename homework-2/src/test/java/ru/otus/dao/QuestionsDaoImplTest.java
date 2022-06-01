package ru.otus.dao;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import ru.otus.domain.AnswerOptions;
import ru.otus.domain.Question;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

/**
 * @author Прохоренко Виктор
 */
@DisplayName("Сервис получения списка вопросов из CSV файла должен")
@ExtendWith(MockitoExtension.class)
class QuestionsDaoImplTest {
    @Mock
    Resource questions;
    @InjectMocks
    private QuestionsDaoImpl questionsDao;

    @Test
    @SneakyThrows
    @DisplayName("Сформировать список вопросов с вариантами ответов")
    void shouldReturnCorrectQuestionListFromCsv() {
        ByteArrayInputStream in = new ByteArrayInputStream("How many fingers are on the hand?,5,4,3,2".getBytes());
        Mockito.when(questions.getInputStream()).thenReturn(in);

        List<Question> expectedQuestions = getExpectedQuestionList();
        List<Question> questionsActual = questionsDao.getQuestions();
        assertNotNull(questionsActual);
        assertThat(expectedQuestions.get(0)).isEqualTo(questionsActual.get(0));

    }

    private List<Question> getExpectedQuestionList() {
        AnswerOptions answerOptions = new AnswerOptions(new ArrayList<>(Arrays.asList("5", "4", "3", "2")), "5");
        Question question = Question.builder()
                .questionMessage("How many fingers are on the hand?")
                .answerOptions(answerOptions)
                .build();
        return Collections.singletonList(question);
    }
}

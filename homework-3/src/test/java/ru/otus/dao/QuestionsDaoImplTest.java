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
import ru.otus.service.LocalizationMessageService;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Прохоренко Виктор
 */
@DisplayName("Сервис получения списка вопросов из CSV файла должен")
@ExtendWith(MockitoExtension.class)
class QuestionsDaoImplTest {
    @Mock
    private Resource questions;
    @Mock
    private LocalizationMessageService localizationMessageService;
    @InjectMocks
    private QuestionsDaoImpl questionsDao;

    private final static String QUESTION = "How many fingers are on the hand?";
    private final static String ANSWER_1 = "5";
    private final static String ANSWER_2 = "4";
    private final static String ANSWER_3 = "3";
    private final static String ANSWER_4 = "2";

    @Test
    @SneakyThrows
    @DisplayName("Сформировать список вопросов с вариантами ответов")
    void shouldReturnCorrectQuestionListFromCsv() {
        ByteArrayInputStream in = new ByteArrayInputStream("How many fingers are on the hand?,5,4,3,2".getBytes());

        Mockito.when(questions.getInputStream()).thenReturn(in);
        Mockito.when(localizationMessageService.getLocalizationMessage(QUESTION)).thenReturn(QUESTION);
        Mockito.when(localizationMessageService.getLocalizationMessage(ANSWER_1)).thenReturn(ANSWER_1);
        Mockito.when(localizationMessageService.getLocalizationMessage(ANSWER_2)).thenReturn(ANSWER_2);
        Mockito.when(localizationMessageService.getLocalizationMessage(ANSWER_3)).thenReturn(ANSWER_3);
        Mockito.when(localizationMessageService.getLocalizationMessage(ANSWER_4)).thenReturn(ANSWER_4);

        List<Question> expectedQuestions = getExpectedQuestionList();
        List<Question> questionsActual = questionsDao.getQuestions();

        assertThat(expectedQuestions)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyInAnyOrderElementsOf(questionsActual);
    }

    private List<Question> getExpectedQuestionList() {
        List<String> answers = new ArrayList<>(Arrays.asList(ANSWER_1, ANSWER_2, ANSWER_3, ANSWER_4));
        AnswerOptions answerOptions = new AnswerOptions((answers), ANSWER_1);
        Question question = new Question(QUESTION, answerOptions);
        return Collections.singletonList(question);
    }
}

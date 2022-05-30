package ru.otus.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.Main;
import ru.otus.domain.AnswerOptions;
import ru.otus.domain.Question;
import ru.otus.exceptions.QuestionsLoadingException;
import ru.otus.service.QuestionsServiceImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

/**
 * @author Прохоренко Виктор
 */
@DisplayName("Сервис получения списка вопросов из CSV файла должен")
@TestPropertySource("/application-test.properties")
@ContextConfiguration(classes = Main.class)
@ExtendWith(SpringExtension.class)
class QuestionsServiceImplTest {
    @Autowired
    private QuestionsServiceImpl questionsService;

    @Test
    @DisplayName("Сформировать список вопросов с вариантами ответов")
    void shouldReturnCorrectQuestionListFromCsv() throws QuestionsLoadingException {
        List<Question> expectedQuestions = getExpectedQuestionList();
        List<Question> questionsActual = questionsService.getQuestions();
        assertNotNull(questionsActual);
        assertThat(expectedQuestions.get(0)).isEqualTo(questionsActual.get(0));

    }

    private List<Question> getExpectedQuestionList() {
        AnswerOptions answerOptions = AnswerOptions.builder()
                .answers(Arrays.asList("5", "4", "3", "2"))
                .rightAnswer("5")
                .build();
        Question question = Question.builder()
                .questionMessage("How many fingers are on the hand?")
                .answerOptions(answerOptions)
                .build();
        return Collections.singletonList(question);
    }
}

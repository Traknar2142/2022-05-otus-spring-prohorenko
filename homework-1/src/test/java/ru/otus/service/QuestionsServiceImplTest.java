package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.domain.Question;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Прохоренко Виктор
 */
@DisplayName("Сервис получения списка вопросов из CSV файла должен")
@ContextConfiguration("/test-spring-context.xml")
@ExtendWith(SpringExtension.class)
class QuestionsServiceImplTest{
    @Autowired
    private QuestionsServiceImpl questionsService;

    @Test
    @DisplayName("Сформировать список вопросов с вариантами ответов")
    void shouldReturnCorrectQuestionListFromCsv() throws IOException {
        List<Question> expectedQuestions = getExpectedQuestionList();
        List<Question> questionsActual = questionsService.getQuestionsFromCsv();
        assertNotNull(questionsActual);
        assertThat(expectedQuestions.get(0)).isEqualTo(questionsActual.get(0));

    }

    List<Question> getExpectedQuestionList() {
        Question question = new Question();
        question.setQuestionMessage("How many fingers are on the hand?");
        question.setAnswerOptions(Arrays.asList("5", "4", "3", "2"));
        return Collections.singletonList(question);
    }
}

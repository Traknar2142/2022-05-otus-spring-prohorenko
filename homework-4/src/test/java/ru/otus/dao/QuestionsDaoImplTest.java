package ru.otus.dao;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.domain.AnswerOptions;
import ru.otus.domain.Question;
import ru.otus.service.ResourceProvider;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Прохоренко Виктор
 */
@DisplayName("Дао получения списка вопросов из CSV файла должен")
@SpringBootTest(classes = {QuestionsDaoImpl.class})
class QuestionsDaoImplTest {
    @MockBean
    private ResourceProvider resourceProvider;
    @Autowired
    private QuestionsDao questionsDao;

    @Test
    @SneakyThrows
    @DisplayName("Сформировать список вопросов с вариантами ответов")
    void shouldReturnCorrectQuestionListFromCsv() {
        Mockito.when(resourceProvider.getResourceDependsOfLocale()).thenReturn(new ClassPathResource("questions.csv"));
        List<Question> expectedQuestions = getExpectedQuestionList();
        List<Question> questionsActual = questionsDao.getQuestions();
        assertThat(expectedQuestions)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyInAnyOrderElementsOf(questionsActual);
    }

    private List<Question> getExpectedQuestionList() {
        AnswerOptions answerOptions = new AnswerOptions(new ArrayList<>(Arrays.asList("5", "4", "3", "2")), "5");
        String questionMessage = "How many fingers are on the hand?";
        Question question = new Question(questionMessage, answerOptions);
        return Collections.singletonList(question);
    }
}

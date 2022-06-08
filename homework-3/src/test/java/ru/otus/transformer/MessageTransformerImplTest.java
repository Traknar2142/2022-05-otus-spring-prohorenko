package ru.otus.transformer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.Application;
import ru.otus.domain.AnswerOptions;
import ru.otus.domain.Question;
import ru.otus.service.LocalizationMessageService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Прохоренко Виктор
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Сервис трансформации контента в сообщение должен")
public class MessageTransformerImplTest {
    @Mock
    private LocalizationMessageService localizationMessageService;
    @InjectMocks
    private MessageTransformerImpl messageTransformer;

    private static final String QUESTION = "How many fingers are on the hand?\n"
            + "1) 5\n"
            + "2) 4\n"
            + "3) 3\n"
            + "4) 2";

    private static final String OPTION_NOT_FOUND = "option-not-found";

    @Test
    @DisplayName("Возвращать значение выбранного варианта ответа")
    void shouldReturnValueOfInputAnswerOption() {
        Mockito.when(localizationMessageService.getLocalizationMessage(OPTION_NOT_FOUND)).thenReturn(OPTION_NOT_FOUND);
        String expectedAnswerContent = "2";
        String actualAnswerContent = messageTransformer.transformAnswerOptionString("4", QUESTION);
        assertThat(actualAnswerContent).isEqualTo(expectedAnswerContent);
    }

    @Test
    @DisplayName("Возвращать Option not found если выбранного варианта нет в списке")
    void shouldReturnONFValueIfInputValueIsNotContainInAnswerValues() {
        Mockito.when(localizationMessageService.getLocalizationMessage(OPTION_NOT_FOUND)).thenReturn(OPTION_NOT_FOUND);
        String actualAnswerContent = messageTransformer.transformAnswerOptionString("6", QUESTION);
        assertThat(actualAnswerContent).isEqualTo(OPTION_NOT_FOUND);
    }

    @Test
    @DisplayName("Возвращать текстовой представление вопроса")
    void shouldReturnStringRepresentationOfQuestion(){
        Question question = getQuestion();
        String actual = messageTransformer.transformOutputQuestionWithAnswerOptions(question);
        Optional<String> questionMessage = actual.lines().filter(line -> line.equals(question.getQuestionMessage())).findFirst();
        assertThat(questionMessage).isPresent();
        assertThat(questionMessage.get()).isEqualTo(question.getQuestionMessage());
    }

    private Question getQuestion() {
        String questionMessage = "How many fingers are on the hand?";
        AnswerOptions answerOptions = new AnswerOptions(new ArrayList<>(Arrays.asList("5", "4", "3", "2")), "5");
        return new Question(questionMessage, answerOptions);
    }
}

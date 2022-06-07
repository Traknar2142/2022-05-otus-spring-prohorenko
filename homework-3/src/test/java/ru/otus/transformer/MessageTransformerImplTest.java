package ru.otus.transformer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.Application;
import ru.otus.domain.AnswerOptions;
import ru.otus.domain.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Прохоренко Виктор
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Application.class)
@Profile("test")
@DisplayName("Сервис трансформации контента в сообщение должен")
public class MessageTransformerImplTest {
    private static final String QUESTION = "How many fingers are on the hand?\n"
            + "1) 5\n"
            + "2) 4\n"
            + "3) 3\n"
            + "4) 2";

    @Autowired
    private MessageTransformerImpl messageTransformer;

    @Test
    @DisplayName("Возвращать значение выбранного варианта ответа")
    void shouldReturnValueOfInputAnswerOption() {
        String expectedAnswerContent = "2";
        String actualAnswerContent = messageTransformer.transformAnswerOptionString("4", QUESTION);
        assertThat(actualAnswerContent).isEqualTo(expectedAnswerContent);
    }

    @Test
    @DisplayName("Возвращать Option not found если выбранного варианта нет в списке")
    void shouldReturnONFValueIfInputValueIsNotContainInAnswerValues() {
        String expectedAnswerContent = "Option not found";
        String actualAnswerContent = messageTransformer.transformAnswerOptionString("6", QUESTION);
        assertThat(actualAnswerContent).isEqualTo(expectedAnswerContent);
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

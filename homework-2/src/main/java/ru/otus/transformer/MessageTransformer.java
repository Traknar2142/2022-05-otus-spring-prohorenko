package ru.otus.transformer;

import ru.otus.domain.Question;

/**
 * @author Прохоренко Виктор
 */
public interface MessageTransformer {
    String transformOutputQuestionWithAnswerOptions(Question question);

    String transformAnswerOptionString(String answer, String outputMessage);
}

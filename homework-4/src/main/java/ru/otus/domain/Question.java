package ru.otus.domain;

import lombok.Data;

/**
 * @author Прохоренко Виктор
 */
@Data
public class Question {
    private String questionMessage;
    private AnswerOptions answerOptions;

    public Question(String questionMessage, AnswerOptions answerOptions) {
        this.questionMessage = questionMessage;
        this.answerOptions = answerOptions;
    }
}

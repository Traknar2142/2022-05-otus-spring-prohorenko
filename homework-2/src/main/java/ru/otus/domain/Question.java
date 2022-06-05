package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

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

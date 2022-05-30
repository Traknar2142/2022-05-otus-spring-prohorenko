package ru.otus.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Прохоренко Виктор
 */
@Data
@Builder
public class Question {
    private String questionMessage;
    private AnswerOptions answerOptions;
}

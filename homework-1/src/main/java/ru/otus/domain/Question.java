package ru.otus.domain;

import lombok.Data;

import java.util.List;

/**
 * @author Прохоренко Виктор
 */
@Data
public class Question {
    private String questionMessage;
    private List<String> answerOptions;
}

package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Прохоренко Виктор
 */
@Data
@AllArgsConstructor
public class AnswerOptions {
    private List<String> answers;
    private String rightAnswer;
}

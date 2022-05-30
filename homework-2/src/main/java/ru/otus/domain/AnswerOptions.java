package ru.otus.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Прохоренко Виктор
 */
@Data
@Builder
public class AnswerOptions {
    private List<String> answers;
    private String rightAnswer;
}

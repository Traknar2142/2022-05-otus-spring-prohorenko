package ru.otus.service;

import ru.otus.domain.Question;
import ru.otus.exceptions.QuestionsLoadingException;

import java.io.IOException;
import java.util.List;

/**
 * @author Прохоренко Виктор
 */
public interface QuestionsService {
    List<Question> getQuestions() throws QuestionsLoadingException;
}

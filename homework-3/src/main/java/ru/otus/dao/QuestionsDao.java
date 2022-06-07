package ru.otus.dao;

import ru.otus.domain.Question;
import ru.otus.exceptions.QuestionsLoadingException;

import java.util.List;

/**
 * @author Прохоренко Виктор
 */
public interface QuestionsDao {
    List<Question> getQuestions() throws QuestionsLoadingException;
}

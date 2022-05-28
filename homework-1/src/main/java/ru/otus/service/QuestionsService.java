package ru.otus.service;

import ru.otus.domain.Question;

import java.io.IOException;
import java.util.List;

/**
 * @author Прохоренко Виктор
 */
public interface QuestionsService {
    List<Question> getQuestionsFromCsv() throws IOException;
}

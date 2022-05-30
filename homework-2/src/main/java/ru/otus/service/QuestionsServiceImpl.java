package ru.otus.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.otus.domain.AnswerOptions;
import ru.otus.domain.Question;
import ru.otus.exceptions.QuestionsLoadingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Прохоренко Виктор
 */
@Service
public class QuestionsServiceImpl implements QuestionsService {
    private final Resource questions;

    public QuestionsServiceImpl(@Value("${questions-file}") Resource questions) {
        this.questions = questions;
    }

    @Override
    public List<Question> getQuestions() throws QuestionsLoadingException {
        try (CSVParser csvRecords = getParser(questions)) {
            return getQuestionList(csvRecords);
        } catch (IOException e) {
            throw new QuestionsLoadingException("Ошибка чтения ресурса " + e.getMessage());
        }
    }

    private List<Question> getQuestionList(CSVParser csvRecords) {
        List<Question> questionsList = new ArrayList<>();
        for (CSVRecord csvRecord : csvRecords) {
            Question question = getQuestion(csvRecord);
            questionsList.add(question);
        }
        return questionsList;
    }

    private Question getQuestion(CSVRecord csvRecord) {
        return Question.builder()
                .questionMessage(csvRecord.get("Question"))
                .answerOptions(getAnswerOptions(csvRecord))
                .build();
    }

    private AnswerOptions getAnswerOptions(CSVRecord csvRecord) {
        List<String> answers = new ArrayList<>();
        answers.add(csvRecord.get("FirstAnswer"));
        answers.add(csvRecord.get("SecondAnswer"));
        answers.add(csvRecord.get("ThirdAnswer"));
        answers.add(csvRecord.get("ForthAnswer"));
        String rightAnswer = csvRecord.get("FirstAnswer");
        return AnswerOptions.builder()
                .answers(answers)
                .rightAnswer(rightAnswer)
                .build();
    }

    private CSVParser getParser(Resource questions) throws IOException {
        InputStream inputStream = questions.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        return new CSVParser(bufferedReader, CSVFormat.DEFAULT
                .withHeader("Question", "FirstAnswer", "SecondAnswer", "ThirdAnswer", "ForthAnswer")
                .withIgnoreHeaderCase()
                .withTrim());

    }
}

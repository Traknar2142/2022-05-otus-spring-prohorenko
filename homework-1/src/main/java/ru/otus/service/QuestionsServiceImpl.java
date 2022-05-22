package ru.otus.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.Resource;
import ru.otus.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Прохоренко Виктор
 */
@AllArgsConstructor
public class QuestionsServiceImpl implements QuestionsService {
    private final Resource questions;

    @Override
    public List<Question> getQuestionsFromCsv() throws IOException {
        CSVParser csvRecords = null;
        csvRecords = getParser(questions);
        return getQuestionList(csvRecords);
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
        Question question = new Question();
        question.setQuestionMessage(csvRecord.get("Question"));
        question.setAnswerOptions(getAnswerOptionsList(csvRecord));
        return question;
    }

    private List<String> getAnswerOptionsList(CSVRecord csvRecord) {
        List<String> answerOptions = new ArrayList<>();
        answerOptions.add(csvRecord.get("FirstAnswer"));
        answerOptions.add(csvRecord.get("SecondAnswer"));
        answerOptions.add(csvRecord.get("ThirdAnswer"));
        answerOptions.add(csvRecord.get("ForthAnswer"));
        return answerOptions;
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

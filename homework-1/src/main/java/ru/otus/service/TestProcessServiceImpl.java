package ru.otus.service;

import lombok.AllArgsConstructor;
import ru.otus.domain.Question;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * @author Прохоренко Виктор
 */
@AllArgsConstructor
public class TestProcessServiceImpl implements TestProcessService {
    private final QuestionsService questionsService;

    @Override
    public void testProcess() {
        try {
            executeTest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void executeTest() throws IOException {
        List<Question> questions = questionsService.getQuestionsFromCsv();
        Scanner scanner = new Scanner(System.in);
        for (Question question : questions) {
            String outputQuestionWithAnswerOptions = getOutputQuestionWithAnswerOptions(question);
            System.out.println(outputQuestionWithAnswerOptions);
            scanner.nextLine();
        }
    }

    private String getOutputQuestionWithAnswerOptions(Question question) {
        StringBuilder builder = new StringBuilder();
        builder.append(question.getQuestionMessage()).append("\n");
        for (String answer : question.getAnswerOptions()) {
            builder.append(answer).append("\n");
        }
        return builder.toString();
    }
}

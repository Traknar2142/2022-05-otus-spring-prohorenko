package ru.otus.transformer;

import org.springframework.stereotype.Service;
import ru.otus.domain.Question;
import ru.otus.service.LocalizationMessageService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Прохоренко Виктор
 */
@Service
public class MessageTransformerImpl implements MessageTransformer {
    private final LocalizationMessageService localizationMessageService;

    public MessageTransformerImpl(LocalizationMessageService localizationMessageService) {
        this.localizationMessageService = localizationMessageService;
    }

    @Override
    public String transformOutputQuestionWithAnswerOptions(Question question) {
        int count = 1;
        StringBuilder builder = new StringBuilder();
        builder.append(question.getQuestionMessage()).append("\n");
        List<String> randomAnswer = getRandomAnswersList(question.getAnswerOptions().getAnswers());
        for (String answer : randomAnswer) {
            builder.append(count).append(") ").append(answer).append("\n");
            count++;
        }
        return builder.toString();
    }
    @Override
    public String transformAnswerOptionString(String answer, String outputQuestionWithAnswerOptions) {
        String optionNotFound = localizationMessageService.getLocalizationMessage("option-not-found");
        String content = outputQuestionWithAnswerOptions
                .lines()
                .filter(line -> line.contains(answer + ")"))
                .findFirst()
                .orElse(optionNotFound);
        if (!content.equals(optionNotFound)) {
            StringBuilder builder = new StringBuilder(content);
            content = builder.substring(3, builder.length());
        }
        return content;
    }

    private List<String> getRandomAnswersList(List<String> answers) {
        List<String> resultList = new ArrayList<>();
        int countOfIndexes = answers.size() - 1;
        while (countOfIndexes >= 0) {
            int randomIndex = rnd(countOfIndexes);
            String answer = answers.get(randomIndex);
            resultList.add(answer);
            answers.remove(randomIndex);
            countOfIndexes--;
        }
        return resultList;
    }

    private int rnd(int max) {
        return (int) (Math.random() * ++max);
    }
}

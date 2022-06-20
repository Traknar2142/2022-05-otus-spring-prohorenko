package ru.otus.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.dao.QuestionsDao;
import ru.otus.domain.Question;
import ru.otus.domain.User;
import ru.otus.exceptions.QuestionsLoadingException;
import ru.otus.transformer.MessageTransformer;

import java.util.List;

/**
 * @author Прохоренко Виктор
 */
@Service
public class TestProcessServiceImpl implements TestProcessService {
    private final QuestionsDao questionsDao;
    private final MessageTransformer transformer;
    private final MessageDialogService messageDialogService;
    private final LocalizationMessageService localizationMessageService;
    private final int passingScore;

    public TestProcessServiceImpl(QuestionsDao questionsDao,
                                  MessageTransformer transformer,
                                  @Value("${minimum-right-answers}") int passingScore,
                                  MessageDialogService messageDialogService,
                                  LocalizationMessageService localizationMessageService) {
        this.questionsDao = questionsDao;
        this.transformer = transformer;
        this.messageDialogService = messageDialogService;
        this.passingScore = passingScore;
        this.localizationMessageService = localizationMessageService;
    }

    @Override
    public void testProcess() {
        try {
            executeTest();
        } catch (QuestionsLoadingException e) {
            String errorMessage = localizationMessageService.getLocalizationMessage("error-file-read-message");
            messageDialogService.outputMessage(errorMessage + e.getMessage());
            e.printStackTrace();
        }
    }

    private void executeTest() throws QuestionsLoadingException {
        User user = introduceUser();
        List<Question> questions = questionsDao.getQuestions();
        int countOfRightAnswers = 0;
        for (Question question : questions) {
            String outputQuestionWithAnswerOptions = transformer.transformOutputQuestionWithAnswerOptions(question);
            messageDialogService.outputMessage(outputQuestionWithAnswerOptions);
            String inputAnswer = messageDialogService.inputMessage();
            String answerOption = transformer.transformAnswerOptionString(inputAnswer, outputQuestionWithAnswerOptions);
            if (answerIsRight(answerOption, question)){
                countOfRightAnswers++;
            }
        }
        showResult(countOfRightAnswers);
    }

    private User introduceUser() {
        String askFirstName = localizationMessageService.getLocalizationMessage("ask-first-name");
        String askLastName = localizationMessageService.getLocalizationMessage("ask-last-name");
        messageDialogService.outputMessage(askFirstName);
        String firstName = messageDialogService.inputMessage();
        messageDialogService.outputMessage(askLastName);
        String lastName = messageDialogService.inputMessage();
        return new User(firstName, lastName);
    }

    private boolean answerIsRight(String answerOption, Question question){
        return question.getAnswerOptions().getRightAnswer().equals(answerOption);
    }

    private void showResult(int countOfRightAnswers){
        if (countOfRightAnswers >= passingScore){
            String testSuccessfulMessage = localizationMessageService.getLocalizationMessage("test-successful");
            messageDialogService.outputMessage(testSuccessfulMessage);
        }else{
            String testFailedMessage = localizationMessageService.getLocalizationMessage("test-failed");
            messageDialogService.outputMessage(testFailedMessage);
        }
        String scoreMessage = localizationMessageService.getLocalizationMessage("your-score");
        messageDialogService.outputMessage(scoreMessage + " " + countOfRightAnswers);
    }
}

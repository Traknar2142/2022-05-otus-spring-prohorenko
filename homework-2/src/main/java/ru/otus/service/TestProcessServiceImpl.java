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
    private final int passingScore;
    private final static String ASK_FIRST_NAME = "Input your first name";
    private final static String ASK_LAST_NAME = "Input your last name";

    public TestProcessServiceImpl(QuestionsDao questionsDao,
                                  MessageTransformer transformer,
                                  @Value ("${minimum-right-answers}") int passingScore,
                                  MessageDialogService messageDialogService) {
        this.questionsDao = questionsDao;
        this.transformer = transformer;
        this.messageDialogService = messageDialogService;
        this.passingScore = passingScore;
    }

    @Override
    public void testProcess() {
        try {
            executeTest();
        } catch (QuestionsLoadingException e) {
            messageDialogService.outputMessage("Ошибка чтения файла с вопросами " + e.getMessage());
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
        messageDialogService.outputMessage(ASK_FIRST_NAME);
        String firstName = messageDialogService.inputMessage();
        messageDialogService.outputMessage(ASK_LAST_NAME);
        String lastName = messageDialogService.inputMessage();
        return new User(firstName, lastName);
    }

    private boolean answerIsRight(String answerOption, Question question){
        return question.getAnswerOptions().getRightAnswer().equals(answerOption);
    }

    private void showResult(int countOfRightAnswers){
        if (countOfRightAnswers >= passingScore){
            messageDialogService.outputMessage("Test passed successfully");
        }else{
            messageDialogService.outputMessage("Test failed");
        }
        messageDialogService.outputMessage("Your score " + countOfRightAnswers);
    }
}

package ru.otus.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
    private final QuestionsService questionsService;
    private final MessageTransformer transformer;
    private TestingMessageDialogService testingMessageDialogService;
    private final int passingScore;
    private final static String ASK_FIRST_NAME = "Input your first name";
    private final static String ASK_LAST_NAME = "Input your last name";

    public TestProcessServiceImpl(QuestionsService questionsService,
                                  MessageTransformer transformer,
                                  @Value ("${minimum-right-answers}") int passingScore) {
        this.questionsService = questionsService;
        this.transformer = transformer;
        this.testingMessageDialogService = new TestingMessageDialogServiceImpl(System.in);
        this.passingScore = passingScore;
    }

    @Override
    public void testProcess() {
        try {
            executeTest();
        } catch (QuestionsLoadingException e) {
            System.out.println("Ошибка чтения файла с вопросами " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setTestingMessageDialogService(TestingMessageDialogService testingMessageDialogService){
        this.testingMessageDialogService = testingMessageDialogService;
    }

    private void executeTest() throws QuestionsLoadingException {
        User user = introduceUser();
        List<Question> questions = questionsService.getQuestions();
        int countOfRightAnswers = 0;
        for (Question question : questions) {
            String outputQuestionWithAnswerOptions = transformer.transformOutputQuestionWithAnswerOptions(question);
            testingMessageDialogService.outputMessage(outputQuestionWithAnswerOptions);
            String inputAnswer = testingMessageDialogService.inputMessage();
            String answerOption = transformer.transformAnswerOptionString(inputAnswer, outputQuestionWithAnswerOptions);
            if (answerIsRight(answerOption, question)){
                countOfRightAnswers++;
            }
        }
        showResult(countOfRightAnswers);
    }

    private User introduceUser() {
        testingMessageDialogService.outputMessage(ASK_FIRST_NAME);
        String firstName = testingMessageDialogService.inputMessage();
        testingMessageDialogService.outputMessage(ASK_LAST_NAME);
        String lastName = testingMessageDialogService.inputMessage();
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }

    private boolean answerIsRight(String answerOption, Question question){
        return question.getAnswerOptions().getRightAnswer().equals(answerOption);
    }

    private void showResult(int countOfRightAnswers){
        if (countOfRightAnswers >= passingScore){
            testingMessageDialogService.outputMessage("Test passed successfully");
        }else{
            testingMessageDialogService.outputMessage("Test failed");
        }
        testingMessageDialogService.outputMessage("Your score " + countOfRightAnswers);
    }
}

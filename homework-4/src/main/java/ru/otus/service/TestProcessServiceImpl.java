package ru.otus.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.dao.QuestionsDao;
import ru.otus.domain.Question;
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
    private final MessageFacade messageFacade;
    private final int passingScore;

    public TestProcessServiceImpl(QuestionsDao questionsDao,
                                  MessageTransformer transformer,
                                  @Value("${minimum-right-answers}") int passingScore,
                                  MessageFacadeImpl messageFacade) {
        this.questionsDao = questionsDao;
        this.transformer = transformer;
        this.passingScore = passingScore;
        this.messageFacade = messageFacade;
    }

    @Override
    public void testProcess() {
        try {
            executeTest();
        } catch (QuestionsLoadingException e) {
            String errorMessage = messageFacade.getLocalizationMessage("error-file-read-message");
            messageFacade.outputMessage(errorMessage + e.getMessage());
            e.printStackTrace();
        }
    }

    private void executeTest() throws QuestionsLoadingException {
        List<Question> questions = questionsDao.getQuestions();
        int countOfRightAnswers = 0;
        for (Question question : questions) {
            String outputQuestionWithAnswerOptions = transformer.transformOutputQuestionWithAnswerOptions(question);
            messageFacade.outputMessage(outputQuestionWithAnswerOptions);
            String inputAnswer = messageFacade.inputMessage();
            String answerOption = transformer.transformAnswerOptionString(inputAnswer, outputQuestionWithAnswerOptions);
            if (answerIsRight(answerOption, question)){
                countOfRightAnswers++;
            }
        }
        showResult(countOfRightAnswers);
    }

    private boolean answerIsRight(String answerOption, Question question){
        return question.getAnswerOptions().getRightAnswer().equals(answerOption);
    }

    private void showResult(int countOfRightAnswers){
        if (countOfRightAnswers >= passingScore){
            messageFacade.outputLocalizedMessage("test-successful");
        }else{
            messageFacade.outputLocalizedMessage("test-failed");
        }
        String scoreMessage = messageFacade.getLocalizationMessage("your-score");
        messageFacade.outputMessage(scoreMessage + " " + countOfRightAnswers);
    }
}

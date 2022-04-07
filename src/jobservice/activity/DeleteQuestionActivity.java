package jobservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.converters.QuestionModelConverter;
import jobservice.dynamodb.QuestionDao;
import jobservice.dynamodb.models.Question;
import jobservice.exceptions.QuestionNotFoundException;
import jobservice.models.QuestionModel;
import jobservice.models.requests.DeleteQuestionRequest;
import jobservice.models.results.DeleteQuestionResult;

import javax.inject.Inject;

public class DeleteQuestionActivity implements RequestHandler<DeleteQuestionRequest, DeleteQuestionResult> {
    private final QuestionDao questionDao;
    private QuestionModelConverter questionModelConverter;

    /**
     * Instantiates a new DeleteQuestionActivity object.
     *
     * @param questionDao QuestionDao to access the questions table.
     * @param questionModelConverter QuestionModelConverter to covert {@link Question} into {@link QuestionModel}
     */
    @Inject
    public DeleteQuestionActivity(QuestionDao questionDao, QuestionModelConverter questionModelConverter) {
        this.questionDao = questionDao;
        this.questionModelConverter = questionModelConverter;
    }

    /**
     * This method handles the incoming request by deleting the question from the database
     * with the provided username and question ID from the request.
     * <p>
     * It then returns the deleted question.
     * <p>
     * If the question does not exist, this should throw a QuestionNotFoundException.
     *
     * @param deleteQuestionRequest request object containing the username and questionId
     * @return deleteQuestionResult result object containing the API defined {@link QuestionModel}
     */
    @Override
    public DeleteQuestionResult handleRequest(DeleteQuestionRequest deleteQuestionRequest, Context context) throws QuestionNotFoundException {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log("Received Delete Question Request for username: " + deleteQuestionRequest.getUsername()
                + ", questionId: " + deleteQuestionRequest.getQuestionId());

        Question question;
        try {
            question = questionDao.getQuestion(deleteQuestionRequest.getUsername(), deleteQuestionRequest.getQuestionId());
        } catch (QuestionNotFoundException e) {
            throw new QuestionNotFoundException(e.getMessage());
        }

        questionDao.removeQuestion(question);

        QuestionModel questionModel = questionModelConverter.toQuestionModel(question);

        return DeleteQuestionResult.builder()
                .withQuestion(questionModel)
                .build();
    }
}

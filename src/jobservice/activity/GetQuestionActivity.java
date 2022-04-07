package jobservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.converters.QuestionModelConverter;
import jobservice.dynamodb.QuestionDao;
import jobservice.dynamodb.models.Question;
import jobservice.exceptions.QuestionNotFoundException;
import jobservice.models.QuestionModel;
import jobservice.models.requests.GetQuestionRequest;
import jobservice.models.results.GetQuestionResult;

import javax.inject.Inject;

public class GetQuestionActivity implements RequestHandler<GetQuestionRequest, GetQuestionResult> {
    private final QuestionDao questionDao;
    private QuestionModelConverter questionModelConverter;

    /**
     * Instantiates a new GetQuestionActivity object.
     *
     * @param questionDao QuestionDao to access the questions table.
     * @param questionModelConverter QuestionModelConverter to covert {@link Question} into {@link QuestionModel}
     */
    @Inject
    public GetQuestionActivity(QuestionDao questionDao, QuestionModelConverter questionModelConverter) {
        this.questionDao = questionDao;
        this.questionModelConverter = questionModelConverter;
    }

    /**
     * This method handles the incoming request by retrieving the question from the database.
     * <p>
     * It then returns the question.
     * <p>
     * If the question does not exist, this should throw a QuestionNotFoundException.
     *
     * @param getQuestionRequest request object containing the username and questionId
     * @return getQuestionResult result object containing the API defined {@link QuestionModel}
     */
    @Override
    public GetQuestionResult handleRequest(GetQuestionRequest getQuestionRequest, Context context) throws QuestionNotFoundException {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log("Received Get Question Request for username: " + getQuestionRequest.getUsername()
                + ", questionId: " + getQuestionRequest.getQuestionId());

        Question question;
        try {
            question = questionDao.getQuestion(getQuestionRequest.getUsername(), getQuestionRequest.getQuestionId());
        } catch (QuestionNotFoundException e) {
            throw new QuestionNotFoundException(e.getMessage());
        }

        QuestionModel questionModel = questionModelConverter.toQuestionModel(question);

        return GetQuestionResult.builder()
                .withQuestion(questionModel)
                .build();
    }
}

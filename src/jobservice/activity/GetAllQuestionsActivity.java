package jobservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.converters.QuestionModelConverter;
import jobservice.dynamodb.QuestionDao;
import jobservice.dynamodb.models.Question;
import jobservice.models.QuestionModel;
import jobservice.models.requests.GetAllQuestionsRequest;
import jobservice.models.results.GetAllQuestionsResult;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class GetAllQuestionsActivity implements RequestHandler<GetAllQuestionsRequest, GetAllQuestionsResult> {
    private final QuestionDao questionDao;
    private QuestionModelConverter questionModelConverter;

    /**
     * Instantiates a new GetAllQuestionsActivity object.
     *
     * @param questionDao QuestionDao to access the questions table.
     * @param converter QuestionModelConverter to covert {@link Question} into {@link QuestionModel}
     */
    @Inject
    public GetAllQuestionsActivity(QuestionDao questionDao, QuestionModelConverter converter) {
        this.questionDao = questionDao;
        this.questionModelConverter = converter;
    }

    /**
     * This method handles the incoming request by retrieving All the questions from the database
     * for that username.
     * <p>
     * It then returns the list of questions.
     * <p>
     *
     * @param getAllQuestionsRequest request object containing the username
     * @return getAllQuestionsResult result object containing a list of the API defined {@link QuestionModel}
     */
    @Override
    public GetAllQuestionsResult handleRequest(GetAllQuestionsRequest getAllQuestionsRequest, Context context) {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log("Received Get All Questions Request for username: " + getAllQuestionsRequest.getUsername());

        String username = getAllQuestionsRequest.getUsername();

        List<Question> questionsFromDB = questionDao.getAllQuestionsByNeedsWork(username);

        ArrayList<Question> questions = null;
        if (questionsFromDB != null) {
            questions = new ArrayList<>(questionsFromDB);
        }

        if (getAllQuestionsRequest.isOnlyNeedsWork()) {
            questions.removeIf(question -> !question.getNeedsWork());
        }

        List<QuestionModel> questionModels = null;
        if (questions != null) {
            questionModels = questionModelConverter.toQuestionModelList(questions);
        }
        
        return GetAllQuestionsResult.builder()
                .withQuestions(questionModels)
                .build();
    }
}

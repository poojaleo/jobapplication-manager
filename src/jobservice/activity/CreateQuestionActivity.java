package jobservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.converters.QuestionModelConverter;
import jobservice.dynamodb.QuestionDao;
import jobservice.dynamodb.models.Question;
import jobservice.exceptions.InvalidAttributeValueException;
import jobservice.exceptions.QuestionAlreadyExistsException;
import jobservice.exceptions.QuestionNotFoundException;
import jobservice.models.QuestionModel;
import jobservice.models.requests.CreateQuestionRequest;
import jobservice.models.results.CreateQuestionResult;
import jobservice.util.JobTrackerServiceUtility;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import static jobservice.util.JobTrackerServiceUtility.isValidString;


/**
 * Implementation of the CreateQuestionActivity for the JobService's CreateQuestion API.
 *
 * This API allows the customer to create a new question with no answer.
 */
public class CreateQuestionActivity implements RequestHandler<CreateQuestionRequest, CreateQuestionResult> {
    private final QuestionDao questionDao;
    private QuestionModelConverter questionModelConverter;

    /**
     * Instantiates a new CreateQuestionActivity object.
     *
     * @param questionDao QuestionDao to access the questions table.
     * @param questionModelConverter QuestionModelConverter to covert {@link Question} into {@link QuestionModel}
     */
    @Inject
    public CreateQuestionActivity(QuestionDao questionDao, QuestionModelConverter questionModelConverter) {
        this.questionDao = questionDao;
        this.questionModelConverter = questionModelConverter;
    }

    /**
     * This method handles the incoming request by persisting a new question
     * with the provided username, question, needsWork, answer and tags from the request.
     * <p>
     * It then returns the newly created question.
     * <p>
     * If the provided question or answer or tag has invalid characters, throws an
     * InvalidAttributeValueException
     *
     * @param createQuestionRequest request object containing the username, question and needsWork
     *                              associated with it along with the optional answer and tags
     * @return createQuestionResult result object containing the API defined {@link QuestionModel}
     */
    @Override
    public CreateQuestionResult handleRequest(final CreateQuestionRequest createQuestionRequest, Context context) {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log("Received Create Question Request for username: " + createQuestionRequest.getUsername());

        List<Question> existingQuestions = questionDao.getAllQuestions(createQuestionRequest.getUsername());

        if (existingQuestions != null) {
            for (Question question : existingQuestions) {
                if (question.getQuestion().equals(createQuestionRequest.getQuestion())) {
                    throw new QuestionAlreadyExistsException("Question already exists for username: " + createQuestionRequest.getUsername());
                }
            }
        }

        if (!createQuestionRequest.getQuestion().equals("")) {
            if (!isValidString(createQuestionRequest.getQuestion())) {
                throw new InvalidAttributeValueException("Provided question has invalid character values.");
            }
        }

        if (createQuestionRequest.getAnswer() != null && !createQuestionRequest.getAnswer().equals("")) {
            if (!isValidString(createQuestionRequest.getAnswer())) {
                throw new InvalidAttributeValueException("Provided answer has invalid character values.");
            }
        }

        if (createQuestionRequest.getTags() != null) {
            for (String tag :
                    createQuestionRequest.getTags()) {
                if (!tag.equals("") && !isValidString(tag)) {
                    throw new InvalidAttributeValueException("Provided tag has invalid character values.");
                }
            }
        }

        String questionId = JobTrackerServiceUtility.generateId();

        String answer = null;
        if (createQuestionRequest.getAnswer() != null && !createQuestionRequest.getAnswer().equals("")) {
            answer = createQuestionRequest.getAnswer();
        }

        List<String> tags = null;
        if (createQuestionRequest.getTags() != null && !createQuestionRequest.getTags().get(0).equals("")) {
            tags = new ArrayList<>(createQuestionRequest.getTags());
        }

        Question question = Question.builder()
                .withUsername(createQuestionRequest.getUsername())
                .withQuestionId(questionId)
                .withQuestion(createQuestionRequest.getQuestion())
                .withNeedsWork(createQuestionRequest.getNeedsWork())
                .withAnswer(answer)
                .withTags(tags)
                .build();

        questionDao.saveQuestion(question);
        QuestionModel questionModel = questionModelConverter.toQuestionModel(question);

        return CreateQuestionResult.builder()
                .withQuestion(questionModel)
                .build();
    }
}

package jobservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.converters.QuestionModelConverter;
import jobservice.dynamodb.QuestionDao;
import jobservice.dynamodb.models.Question;
import jobservice.exceptions.InvalidAttributeValueException;
import jobservice.exceptions.QuestionNotFoundException;
import jobservice.models.QuestionModel;
import jobservice.models.requests.UpdateQuestionRequest;
import jobservice.models.results.UpdateQuestionResult;

import javax.inject.Inject;

import static jobservice.util.JobTrackerServiceUtility.isValidString;

/**
 * Implementation of the UpdateQuestionActivity for the JobSmarter's UpdateQuestion API.
 *
 * This API allows the customer to update their saved question's information.
 */
public class UpdateQuestionActivity implements RequestHandler<UpdateQuestionRequest, UpdateQuestionResult> {
    private final QuestionDao questionDao;
    private QuestionModelConverter questionModelConverter;

    /**
     * Instantiates a new UpdateQuestionActivity object.
     *
     * @param questionDao QuestionDao to access the questions table.
     * @param questionModelConverter QuestionModelConverter to covert {@link Question} into {@link QuestionModel}
     */
    @Inject
    public UpdateQuestionActivity(QuestionDao questionDao, QuestionModelConverter questionModelConverter) {
        this.questionDao = questionDao;
        this.questionModelConverter = questionModelConverter;
    }

    /**
     * This method handles the incoming request by retrieving the question, updating it,
     * and persisting the question.
     * <p>
     * It then returns the updated question.
     * <p>
     * If the question does not exist, this should throw a QuestionNotFoundException.
     * <p>
     * If the provided question, answer or tags has invalid characters, throws an
     * InvalidAttributeValueException
     * <p>
     *
     * @param updateQuestionRequest request object containing the username, question ID, question, needsWork,
     *                              answer and tags associated with it
     * @return updateQuestionResult result object containing the API defined {@link QuestionModel}
     */
    @Override
    public UpdateQuestionResult handleRequest(final UpdateQuestionRequest updateQuestionRequest, Context context) throws QuestionNotFoundException, InvalidAttributeValueException {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log("Received Update Question Request for username : " + updateQuestionRequest.getUsername()
                + ", questionId: " + updateQuestionRequest.getQuestionId());

        Question questionToUpdate;
        try {
            questionToUpdate = questionDao.getQuestion(updateQuestionRequest.getUsername(), updateQuestionRequest.getQuestionId());
        } catch (QuestionNotFoundException e) {
            throw new QuestionNotFoundException(e.getMessage());
        }

        // if request contains an updated question that is not null, then update the question
        if (!questionToUpdate.getQuestion().equals(updateQuestionRequest.getQuestion()) && updateQuestionRequest.getQuestion() != null && !updateQuestionRequest.getQuestion().equals("")) {
            if (!isValidString(updateQuestionRequest.getQuestion())) {
                throw new InvalidAttributeValueException("Provided question has invalid character values.");
            }
            questionToUpdate.setQuestion(updateQuestionRequest.getQuestion());
        }

        // if request contains an updated needsWork that is not null, then update the needsWork
        if (!questionToUpdate.getNeedsWork().equals(updateQuestionRequest.getNeedsWork()) && updateQuestionRequest.getNeedsWork() != null) {
            questionToUpdate.setNeedsWork(updateQuestionRequest.getNeedsWork());
        }

        // if request contains an updated answer that is not null, then update the answer
        if (updateQuestionRequest.getAnswer() != null && !updateQuestionRequest.getAnswer().equals("")) {
            if (!isValidString(updateQuestionRequest.getAnswer())) {
                throw new InvalidAttributeValueException("Provided answer has invalid character values.");
            }
            questionToUpdate.setAnswer(updateQuestionRequest.getAnswer());
        }

        // if request contains an updated tags that is not null, then update the tags
        if (updateQuestionRequest.getTags() != null && !updateQuestionRequest.getTags().get(0).equals("")) {
            for (String tag :
                    updateQuestionRequest.getTags()) {
                if (!isValidString(tag)) {
                    throw new InvalidAttributeValueException("Provided tag has invalid character values.");
                }
            }
            questionToUpdate.setTags(updateQuestionRequest.getTags());
        }

        questionDao.saveQuestion(questionToUpdate);
        QuestionModel questionModel = questionModelConverter.toQuestionModel(questionToUpdate);

        return UpdateQuestionResult.builder()
                .withQuestion(questionModel)
                .build();
    }
}

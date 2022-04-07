package jobservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import jobservice.dynamodb.models.Question;
import jobservice.exceptions.QuestionNotFoundException;

import javax.inject.Inject;
import java.util.List;

public class QuestionDao {
    private final DynamoDBMapper dynamoDBMapper;

    /**
     * Instantiates a QuestionDao object.
     *
     * @param dynamoDBMapper the {@link DynamoDBMapper} used to interact with the questions table
     */
    @Inject
    public QuestionDao(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    /**
     * Returns the {@link Question} corresponding to the specified username and questionId.
     *
     * @param username the user for this question
     * @param questionId the questionId for this question
     * @return the stored Question from DynamoDB
     * @throws QuestionNotFoundException if question is not found in DB with provided username and questionId
     */
    public Question getQuestion(String username, String questionId) throws QuestionNotFoundException {
        Question question = this.dynamoDBMapper.load(Question.class, username, questionId);

        if (question == null) {
            throw new QuestionNotFoundException("Could not find question for username: " + username +
                    " and with question id: " + questionId);
        }

        return question;
    }

    /**
     * Gets a collection of Question objects for a provided username sorted by needsWork.
     * No guarantee of the order/size of result is provided.
     * Result will be an empty list if no questions for username are found.
     * @param username The username to fetch questions for
     * @return List of Questions (those found for the username provided)
     */
    public List<Question> getAllQuestionsByNeedsWork(String username) {
        Question question = Question.builder().build();
        question.setUsername(username);
        DynamoDBQueryExpression<Question> queryExpression = new DynamoDBQueryExpression<Question>()
                .withHashKeyValues(question)
                .withConsistentRead(false)
                .withIndexName(Question.NEEDSWORK_INDEX);

        List<Question> questionList = dynamoDBMapper.query(Question.class, queryExpression);
        return questionList;
    }

    /**
     * Gets a collection of questions ONLY for a provided username.
     * No guarantee of the order/size of result is provided.
     * Result will be an empty list if no questions for username are found.
     * @param username The username to fetch questions for
     * @return List of questions (those found for the username provided)
     */
    public List<Question> getAllQuestions(String username) {
        Question question = Question.builder().build();
        question.setUsername(username);
        DynamoDBQueryExpression<Question> queryExpression = new DynamoDBQueryExpression<Question>()
                .withHashKeyValues(question)
                .withConsistentRead(false)
                .withIndexName(Question.QUESTION_INDEX);

        List<Question> questionList = dynamoDBMapper.query(Question.class, queryExpression);
        return questionList;
    }

    /**
     * Save the provided Question in our DynamoDB table
     *
     * @param question - a question that been created or updated
     * @return the question that been saved in our Database
     */
    public Question saveQuestion(Question question) {
        dynamoDBMapper.save(question);
        return question;
    }

    /**
     * Remove the provided Question from our DynamoDB table
     *
     * @param question - a question that needs to be removed from our Database
     */
    public void removeQuestion(Question question) {
        dynamoDBMapper.delete(question);
    }
}

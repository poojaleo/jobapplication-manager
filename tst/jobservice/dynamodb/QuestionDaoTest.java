package jobservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import jobservice.dynamodb.models.Question;
import jobservice.exceptions.QuestionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class QuestionDaoTest {
    private final String username = "george";
    private final String questionId = "ruoifdg9fd";
    private final String question = "How does Mockito work?";
    private final Boolean needsWork = false;
    private final String answer = "It mocks the dependencies so you can test the class without relying on the functionality of" +
            "the dependencies.";
    private final List<String> tags = List.of("google", "facebook");

    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @Mock
    private PaginatedQueryList<Question> questionList;

    @InjectMocks
    private QuestionDao questionDao;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        questionDao = new QuestionDao(dynamoDBMapper);
    }

    @Test
    void getQuestion_validUsernameAndQuestionId_returnQuestionFromDynamoDB() {
        // GIVEN - a question
        Question questionToGet = Question.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withQuestion(question)
                .withNeedsWork(needsWork)
                .withAnswer(answer)
                .withTags(tags)
                .build();

        // WHEN - call getQuestion()
        when(dynamoDBMapper.load(Question.class, username, questionId)).thenReturn(questionToGet);
        Question result = questionDao.getQuestion(username, questionId);

        // THEN
        assertEquals(username, result.getUsername(), "The user name for the question should be: " + username);
        assertEquals(questionId, result.getQuestionId(), "The questionId for the question should be: " + questionId);
        assertEquals(question, result.getQuestion(), "The question should be: " + question);
        assertEquals(needsWork, result.getNeedsWork(), "The needsWork should be: " + needsWork);
        assertEquals(answer, result.getAnswer(), "The answer should be: " + answer);
        assertEquals(tags, result.getTags(), "The tags should be: " + tags);
    }

    @Test
    void getQuestion_questionNotInDatabase_throwsQuestionNotFoundException() {
        // GIVEN - non-existing question in DynamoDB table
        String username = "'/hdksg'";
        String questionId = "xyz64dg9fd";

        // WHEN + THEN - getQuestion() should throw QuestionNotFoundException
        when(dynamoDBMapper.load(Question.class, username, questionId)).thenReturn(null);
        assertThrows(QuestionNotFoundException.class, () -> questionDao.getQuestion(username, questionId));
    }

    @Test
    void saveQuestion_provideQuestion_savesQuestionToDynamoDB() {
        // GIVEN - a question
        Question questionToSave = Question.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withQuestion(question)
                .withNeedsWork(needsWork)
                .withAnswer(answer)
                .withTags(tags)
                .build();

        // WHEN - call saveQuestion()
        questionDao.saveQuestion(questionToSave);

        // THEN - verify question was saved to DynamoDB
        verify(dynamoDBMapper).save(questionToSave);
    }

    @Test
    void removeQuestion_provideQuestion_removesQuestionFromDynamoDB() {
        // GIVEN - a question
        Question questionToRemove = Question.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withQuestion(question)
                .withNeedsWork(needsWork)
                .withAnswer(answer)
                .withTags(tags)
                .build();

        // WHEN - call removeQuestion()
        questionDao.removeQuestion(questionToRemove);

        // THEN - verify question was removed from DynamoDB
        verify(dynamoDBMapper).delete(questionToRemove);
    }

    @Test
    public void getAllQuestionsByNeedsWork_usernameHasQuestions_returnsQuestions() {
        // GIVEN
        when(dynamoDBMapper.query(eq(Question.class), any(DynamoDBQueryExpression.class))).thenReturn(questionList);
        ArgumentCaptor<DynamoDBQueryExpression<Question>> captor = ArgumentCaptor.forClass(DynamoDBQueryExpression.class);

        // WHEN
        List<Question> result = questionDao.getAllQuestionsByNeedsWork("mickeymouse");

        // THEN
        assertEquals(questionList, result, "Expected list of questions to be what was returned from dynamodb");

        verify(dynamoDBMapper).query(eq(Question.class), captor.capture());
        Question queriedUsername = captor.getValue().getHashKeyValues();
        assertEquals("mickeymouse", queriedUsername.getUsername(), "Expected query expression to query for " +
                "partition key: mickeymouse");
    }
}

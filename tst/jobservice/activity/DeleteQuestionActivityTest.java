package jobservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import jobservice.converters.QuestionModelConverter;
import jobservice.dynamodb.QuestionDao;
import jobservice.dynamodb.models.Question;
import jobservice.exceptions.QuestionNotFoundException;
import jobservice.models.QuestionModel;
import jobservice.models.requests.DeleteQuestionRequest;
import jobservice.models.results.DeleteQuestionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class DeleteQuestionActivityTest {
    private final String username = "george";
    private final String questionId = "ruoifdg9fd";
    private final String question = "How does Mockito work?";
    private final Boolean needsWork = false;
    private final String answer = "It mocks the dependencies so you can test the class without relying on the functionality of" +
            "the dependencies.";
    private final List<String> tags = List.of("google", "facebook");

    @Mock
    private QuestionDao questionDao;

    @Mock
    private QuestionModelConverter converter;

    @Mock
    private Context context;

    private LambdaLogger lambdaLogger;

    @InjectMocks
    private DeleteQuestionActivity deleteQuestionActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        deleteQuestionActivity = new DeleteQuestionActivity(questionDao, converter);
        lambdaLogger = new LambdaLogger() {
            @Override
            public void log(String message) {
                System.out.println(message);
            }

            @Override
            public void log(byte[] message) {
                System.out.println(message);
            }
        };
    }

    @Test
    void handleRequest_savedQuestionFound_returnsDeletedQuestion() {
        // GIVEN - a DeleteQuestionRequest for a question with username, questionId, question, needsWork, answer and tags
        Question questionToDelete = Question.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withQuestion(question)
                .withNeedsWork(needsWork)
                .withAnswer(answer)
                .withTags(tags)
                .build();

        DeleteQuestionRequest request = DeleteQuestionRequest.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .build();

        QuestionModel questionModel = QuestionModel.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withQuestion(question)
                .withNeedsWork(needsWork)
                .withAnswer(answer)
                .withTags(tags)
                .build();

        // WHEN - call handleRequest()
        when(context.getLogger()).thenReturn(lambdaLogger);
        when(questionDao.getQuestion(username, questionId)).thenReturn(questionToDelete);
        when(converter.toQuestionModel(any(Question.class))).thenReturn(questionModel);
        DeleteQuestionResult result = deleteQuestionActivity.handleRequest(request, context);

        // THEN - returns the deleted Question corresponding to the username and questionId
        verify(questionDao).removeQuestion(questionToDelete);
        assertEquals(username, result.getQuestion().getUsername(), "Expected username to be: " + username);
        assertEquals(questionId, result.getQuestion().getQuestionId(), "Expected questionId to be: " + questionId);
        assertEquals(question, result.getQuestion().getQuestion(), "Expected question to be: " + question);
        assertEquals(needsWork, result.getQuestion().getNeedsWork(), "Expected needsWork to be: " + needsWork);
        assertEquals(answer, result.getQuestion().getAnswer(), "Expected answer to be: " + answer);
        assertEquals(tags, result.getQuestion().getTags(), "Expected tags to be: " + tags);
    }

    @Test
    void handleRequest_invalidUsername_throwsQuestionNotFoundException() {
        // GIVEN - a DeleteQuestionRequest with an INVALID username and questionId
        String invalidUsername = "noSuchUserExists";

        DeleteQuestionRequest request = DeleteQuestionRequest.builder()
                .withUsername(invalidUsername)
                .withQuestionId(questionId)
                .build();

        // WHEN + THEN - handleRequest() should throw an QuestionNotFoundException because of the INVALID username
        when(context.getLogger()).thenReturn(lambdaLogger);
        when(questionDao.getQuestion(invalidUsername, questionId)).thenThrow(QuestionNotFoundException.class);
        assertThrows(QuestionNotFoundException.class, () -> deleteQuestionActivity.handleRequest(request, context));
    }

    @Test
    void handleRequest_invalidQuestionId_throwsQuestionNotFoundException() {
        // GIVEN - a GetQuestionRequest with a username and an INVALID questionId
        String invalidQuestionId = "xyz64dgre7";

        DeleteQuestionRequest request = DeleteQuestionRequest.builder()
                .withUsername(username)
                .withQuestionId(invalidQuestionId)
                .build();

        // WHEN + THEN - handleRequest() should throw an QuestionNotFoundException because of the INVALID questionId
        when(context.getLogger()).thenReturn(lambdaLogger);
        when(questionDao.getQuestion(username, invalidQuestionId)).thenThrow(QuestionNotFoundException.class);
        assertThrows(QuestionNotFoundException.class, () -> deleteQuestionActivity.handleRequest(request, context));
    }
}

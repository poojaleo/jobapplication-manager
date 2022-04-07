package jobservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import jobservice.converters.QuestionModelConverter;
import jobservice.dynamodb.QuestionDao;
import jobservice.dynamodb.models.Question;
import jobservice.exceptions.InvalidAttributeValueException;
import jobservice.exceptions.QuestionNotFoundException;
import jobservice.models.QuestionModel;
import jobservice.models.requests.UpdateQuestionRequest;
import jobservice.models.results.UpdateQuestionResult;
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

public class UpdateQuestionActivityTest {
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
    private UpdateQuestionActivity updateQuestionActivity;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        updateQuestionActivity = new UpdateQuestionActivity(questionDao, converter);
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
    void handleRequest_changeQuestion_updatesQuestion() {
        // GIVEN - an UpdateQuestionRequest with username and questionId for an updated question
        String updatedQuestion = "What is the updated question?";
        Question questionToUpdate = Question.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withQuestion(question)
                .withNeedsWork(needsWork)
                .withAnswer(answer)
                .withTags(tags)
                .build();
        UpdateQuestionRequest request = UpdateQuestionRequest.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withQuestion(updatedQuestion)
                .build();

        Question actualUpdatedQuestion = Question.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withQuestion(updatedQuestion)
                .withNeedsWork(needsWork)
                .withAnswer(answer)
                .withTags(tags)
                .build();

        QuestionModel questionModel = QuestionModel.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withQuestion(updatedQuestion)
                .withNeedsWork(needsWork)
                .withAnswer(answer)
                .withTags(tags)
                .build();

        // WHEN - call handleRequest()
        when(context.getLogger()).thenReturn(lambdaLogger);
        when(questionDao.getQuestion(username, questionId)).thenReturn(questionToUpdate);
        when(converter.toQuestionModel(actualUpdatedQuestion)).thenReturn(questionModel);
        UpdateQuestionResult result = updateQuestionActivity.handleRequest(request, context);

        // THEN - updates only the question for that questionId
        verify(questionDao).saveQuestion(actualUpdatedQuestion);
        assertEquals(username, result.getQuestion().getUsername(), "Expected username to be: " + username);
        assertEquals(questionId, result.getQuestion().getQuestionId(), "Expected questionId to be: " + questionId);
        assertEquals(updatedQuestion, result.getQuestion().getQuestion(), "Expected question to be: " + updatedQuestion);
        assertEquals(needsWork, result.getQuestion().getNeedsWork(), "Expected needsWork to be: " + needsWork);
        assertEquals(answer, result.getQuestion().getAnswer(), "Expected answer to be: " + answer);
        assertEquals(tags, result.getQuestion().getTags(), "Expected tags to be: " + tags);
    }

    @Test
    void handleRequest_changeNeedsWork_updatesNeedsWork() {
        // GIVEN - an UpdateQuestionRequest with username and questionId for an updated needsWork
        Boolean updatedNeedsWork = true;
        Question needsWorkToUpdate = Question.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withQuestion(question)
                .withNeedsWork(needsWork)
                .withAnswer(answer)
                .withTags(tags)
                .build();
        UpdateQuestionRequest request = UpdateQuestionRequest.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withNeedsWork(updatedNeedsWork)
                .build();

        Question updatedQuestionNeedsWork = Question.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withQuestion(question)
                .withNeedsWork(updatedNeedsWork)
                .withAnswer(answer)
                .withTags(tags)
                .build();

        QuestionModel questionModel = QuestionModel.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withQuestion(question)
                .withNeedsWork(updatedNeedsWork)
                .withAnswer(answer)
                .withTags(tags)
                .build();

        // WHEN - call handleRequest()
        when(context.getLogger()).thenReturn(lambdaLogger);
        when(questionDao.getQuestion(username, questionId)).thenReturn(needsWorkToUpdate);
        when(converter.toQuestionModel(any(Question.class))).thenReturn(questionModel);
        UpdateQuestionResult result = updateQuestionActivity.handleRequest(request, context);

        // THEN - updates only the needsWork for that questionId
        verify(questionDao).saveQuestion(updatedQuestionNeedsWork);
        assertEquals(username, result.getQuestion().getUsername(), "Expected username to be: " + username);
        assertEquals(questionId, result.getQuestion().getQuestionId(), "Expected questionId to be: " + questionId);
        assertEquals(question, result.getQuestion().getQuestion(), "Expected question to be: " + question);
        assertEquals(updatedNeedsWork, result.getQuestion().getNeedsWork(), "Expected needsWork to be: " + updatedNeedsWork);
        assertEquals(answer, result.getQuestion().getAnswer(), "Expected answer to be: " + answer);
        assertEquals(tags, result.getQuestion().getTags(), "Expected tags to be: " + tags);
    }

    @Test
    void handleRequest_changeAnswer_updatesAnswer() {
        // GIVEN - an UpdateQuestionRequest with username and questionId for an updated answer
        String updatedAnswer = "This is an updated answer.";
        Question questionToUpdate = Question.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withQuestion(question)
                .withNeedsWork(needsWork)
                .withAnswer(answer)
                .withTags(tags)
                .build();
        UpdateQuestionRequest request = UpdateQuestionRequest.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withAnswer(updatedAnswer)
                .build();

        Question updatedQuestionAnswer = Question.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withQuestion(question)
                .withNeedsWork(needsWork)
                .withAnswer(updatedAnswer)
                .withTags(tags)
                .build();

        QuestionModel questionModel = QuestionModel.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withQuestion(question)
                .withNeedsWork(needsWork)
                .withAnswer(updatedAnswer)
                .withTags(tags)
                .build();

        // WHEN - call handleRequest()
        when(context.getLogger()).thenReturn(lambdaLogger);
        when(questionDao.getQuestion(username, questionId)).thenReturn(questionToUpdate);
        when(converter.toQuestionModel(any(Question.class))).thenReturn(questionModel);
        UpdateQuestionResult result = updateQuestionActivity.handleRequest(request, context);

        // THEN - updates only the answer for that questionId
        verify(questionDao).saveQuestion(updatedQuestionAnswer);
        assertEquals(username, result.getQuestion().getUsername(), "Expected username to be: " + username);
        assertEquals(questionId, result.getQuestion().getQuestionId(), "Expected questionId to be: " + questionId);
        assertEquals(question, result.getQuestion().getQuestion(), "Expected question to be: " + question);
        assertEquals(needsWork, result.getQuestion().getNeedsWork(), "Expected needsWork to be: " + needsWork);
        assertEquals(updatedAnswer, result.getQuestion().getAnswer(), "Expected answer to be: " + updatedAnswer);
        assertEquals(tags, result.getQuestion().getTags(), "Expected tags to be: " + tags);
    }

    @Test
    void handleRequest_changeTags_updatesTags() {
        // GIVEN - an UpdateQuestionRequest with username and questionId for an updated tags
        List<String> updatedTags = List.of("netflix", "microsoft");
        Question questionToUpdate = Question.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withQuestion(question)
                .withNeedsWork(needsWork)
                .withAnswer(answer)
                .withTags(tags)
                .build();
        UpdateQuestionRequest request = UpdateQuestionRequest.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withTags(updatedTags)
                .build();

        Question updatedQuestionTags = Question.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withQuestion(question)
                .withNeedsWork(needsWork)
                .withAnswer(answer)
                .withTags(updatedTags)
                .build();

        QuestionModel questionModel = QuestionModel.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withQuestion(question)
                .withNeedsWork(needsWork)
                .withAnswer(answer)
                .withTags(updatedTags)
                .build();

        // WHEN - call handleRequest()
        when(context.getLogger()).thenReturn(lambdaLogger);
        when(questionDao.getQuestion(username, questionId)).thenReturn(questionToUpdate);
        when(converter.toQuestionModel(any(Question.class))).thenReturn(questionModel);
        UpdateQuestionResult result = updateQuestionActivity.handleRequest(request, context);

        // THEN - updates only the answer for that questionId
        verify(questionDao).saveQuestion(updatedQuestionTags);
        assertEquals(username, result.getQuestion().getUsername(), "Expected username to be: " + username);
        assertEquals(questionId, result.getQuestion().getQuestionId(), "Expected questionId to be: " + questionId);
        assertEquals(question, result.getQuestion().getQuestion(), "Expected question to be: " + question);
        assertEquals(needsWork, result.getQuestion().getNeedsWork(), "Expected needsWork to be: " + needsWork);
        assertEquals(answer, result.getQuestion().getAnswer(), "Expected answer to be: " + answer);
        assertEquals(updatedTags, result.getQuestion().getTags(), "Expected tags to be: " + updatedTags);
    }

    @Test
    void handleRequest_invalidUsername_throwsQuestionNotFoundException() {
        // GIVEN - an UpdateQuestionRequest with an INVALID username and questionId
        String invalidUsername = "noSuchUserExists";

        UpdateQuestionRequest request = UpdateQuestionRequest.builder()
                .withUsername(invalidUsername)
                .withQuestionId(questionId)
                .build();

        // WHEN + THEN - handleRequest() should throw an QuestionNotFoundException because of the INVALID username
        when(context.getLogger()).thenReturn(lambdaLogger);
        when(questionDao.getQuestion(invalidUsername, questionId)).thenThrow(QuestionNotFoundException.class);
        assertThrows(QuestionNotFoundException.class, () -> updateQuestionActivity.handleRequest(request, context));
    }

    @Test
    void handleRequest_invalidQuestionId_throwsQuestionNotFoundException() {
        // GIVEN - a UpdateQuestionRequest with a username and an INVALID questionId
        String invalidQuestionId = "xyz64dgre7";

        UpdateQuestionRequest request = UpdateQuestionRequest.builder()
                .withUsername(username)
                .withQuestionId(invalidQuestionId)
                .build();

        // WHEN + THEN - handleRequest() should throw an QuestionNotFoundException because of the INVALID questionId
        when(context.getLogger()).thenReturn(lambdaLogger);
        when(questionDao.getQuestion(username, invalidQuestionId)).thenThrow(QuestionNotFoundException.class);
        assertThrows(QuestionNotFoundException.class, () -> updateQuestionActivity.handleRequest(request, context));
    }

    @Test
    void handleRequest_invalidQuestion_throwsInvalidAttributeValueException() {
        // GIVEN - a UpdateQuestionRequest with an INVALID question
        String invalidQuestion = "How does\\Mockito work?";
        Question questionToUpdate = Question.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withQuestion(question)
                .withNeedsWork(needsWork)
                .withAnswer(answer)
                .withTags(tags)
                .build();
        UpdateQuestionRequest request = UpdateQuestionRequest.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withQuestion(invalidQuestion)
                .build();

        // WHEN + THEN - handleRequest() should throw an InvalidAttributeValueException because of the INVALID question
        when(context.getLogger()).thenReturn(lambdaLogger);
        when(questionDao.getQuestion(username, questionId)).thenReturn(questionToUpdate);
        assertThrows(InvalidAttributeValueException.class, () -> updateQuestionActivity.handleRequest(request, context));
    }

    @Test
    void handleRequest_invalidAnswer_throwsInvalidAttributeValueException() {
        // GIVEN - a UpdateQuestionRequest with an INVALID answer
        String invalidAnswer = "It mocks the depend\\encies so you can test the class without relying on the functionality of" +
                "the dependencies.";
        Question questionToUpdate = Question.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withQuestion(question)
                .withNeedsWork(needsWork)
                .withAnswer(answer)
                .withTags(tags)
                .build();
        UpdateQuestionRequest request = UpdateQuestionRequest.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withAnswer(invalidAnswer)
                .build();

        // WHEN + THEN - handleRequest() should throw an InvalidAttributeValueException because of the INVALID answer
        when(context.getLogger()).thenReturn(lambdaLogger);
        when(questionDao.getQuestion(username, questionId)).thenReturn(questionToUpdate);
        assertThrows(InvalidAttributeValueException.class, () -> updateQuestionActivity.handleRequest(request, context));
    }

    @Test
    void handleRequest_invalidTags_throwsInvalidAttributeValueException() {
        // GIVEN - a UpdateQuestionRequest with INVALID tags
        List<String> invalidTags = List.of("goo\\gle", "faceb\\ook");
        Question questionToUpdate = Question.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withQuestion(question)
                .withNeedsWork(needsWork)
                .withAnswer(answer)
                .withTags(tags)
                .build();
        UpdateQuestionRequest request = UpdateQuestionRequest.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withTags(invalidTags)
                .build();

        // WHEN + THEN - handleRequest() should throw an InvalidAttributeValueException because of the INVALID tags
        when(context.getLogger()).thenReturn(lambdaLogger);
        when(questionDao.getQuestion(username, questionId)).thenReturn(questionToUpdate);
        assertThrows(InvalidAttributeValueException.class, () -> updateQuestionActivity.handleRequest(request, context));
    }
}

package jobservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import jobservice.converters.QuestionModelConverter;
import jobservice.dynamodb.QuestionDao;
import jobservice.dynamodb.models.Question;
import jobservice.exceptions.InvalidAttributeValueException;
import jobservice.models.QuestionModel;
import jobservice.models.requests.CreateQuestionRequest;
import jobservice.models.results.CreateQuestionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreateQuestionActivityTest {

    private final String usernameMickey = "mickeymouse";

    private final String username = usernameMickey;
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
    private CreateQuestionActivity createQuestionActivity;

    @BeforeEach
    private void setUp() {
        initMocks(this);
        createQuestionActivity = new CreateQuestionActivity(questionDao, converter);
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
    void handleRequest_validRequest_createsNewQuestionWithAnswerAndTags() {
        // GIVEN - CreateQuestionRequest with valid username, question, needsWork, answer and a list of tags
        CreateQuestionRequest request = CreateQuestionRequest.builder()
                .withUsername(username)
                .withQuestion(question)
                .withNeedsWork(needsWork)
                .withAnswer(answer)
                .withTags(tags)
                .build();
        QuestionModel questionModel = QuestionModel.builder()
                .withUsername(username)
                .withQuestion(question)
                .withNeedsWork(needsWork)
                .withAnswer(answer)
                .withTags(tags)
                .build();

        // WHEN - call handleRequest()
        when(context.getLogger()).thenReturn(lambdaLogger);
        when(converter.toQuestionModel(any(Question.class))).thenReturn(questionModel);
        CreateQuestionResult result = createQuestionActivity.handleRequest(request, context);

        // THEN - returns QuestionModel with randomized id, username, question, needsWork, answer and provided tags
        verify(questionDao).saveQuestion(any(Question.class));
        assertEquals(username, result.getQuestion().getUsername(), "The username should be " + username);
        assertEquals(question, result.getQuestion().getQuestion(), "The question should be " + question);
        assertEquals(needsWork, result.getQuestion().getNeedsWork(), "The needsWork should be " + needsWork);
        assertEquals(answer, result.getQuestion().getAnswer(), "The answer should be " + answer);
        assertEquals(tags, result.getQuestion().getTags(), "The question tags should be " + tags);
    }

    @Test
    void handleRequest_requestWithNoAnswerOrTags_createsNewQuestionWithNoAnswerOrTags() {
        // GIVEN - CreateQuestionRequest with valid username, question and needsWork but no answer nor a list of tags
        CreateQuestionRequest request = CreateQuestionRequest.builder()
                .withUsername(username)
                .withQuestion(question)
                .withNeedsWork(needsWork)
                .build();
        QuestionModel questionModel = QuestionModel.builder()
                .withUsername(username)
                .withQuestion(question)
                .withNeedsWork(needsWork)
                .build();

        // WHEN - call handleRequest()
        when(context.getLogger()).thenReturn(lambdaLogger);
        when(converter.toQuestionModel(any(Question.class))).thenReturn(questionModel);
        CreateQuestionResult result = createQuestionActivity.handleRequest(request, context);

        // THEN - returns QuestionModel with randomized id, username, question, and needsWork but null for answer
        // and provided tags
        verify(questionDao).saveQuestion(any(Question.class));
        assertEquals(username, result.getQuestion().getUsername(), "The username should be " + username);
        assertEquals(question, result.getQuestion().getQuestion(), "The question should be " + question);
        assertEquals(needsWork, result.getQuestion().getNeedsWork(), "The needsWork should be " + needsWork);
        assertNull(result.getQuestion().getAnswer(), "The answer should be null as no answer was provided.");
        assertNull(result.getQuestion().getTags(), "The question tags should be null as no tags were provided.");
    }

    @Test
    void handleRequest_invalidQuestion_throwsInvalidAttributeValueException() {
        // GIVEN - CreateQuestionRequest with INVALID question
        String invalidQuestion = "How does\\Mockito work?";
        CreateQuestionRequest request = CreateQuestionRequest.builder()
                .withUsername(username)
                .withQuestion(invalidQuestion)
                .withNeedsWork(needsWork)
                .withAnswer(answer)
                .withTags(tags)
                .build();
        when(context.getLogger()).thenReturn(lambdaLogger);

        // WHEN + THEN - handleRequest() should throw an InvalidAttributeValueException because of the INVALID question
        assertThrows(InvalidAttributeValueException.class, () -> createQuestionActivity.handleRequest(request, context));
    }

    @Test
    void handleRequest_invalidAnswer_throwsInvalidAttributeValueException() {
        // GIVEN - CreateQuestionRequest with INVALID answer
        String invalidAnswer = "It mocks the depend\\encies so you can test the class without relying on the functionality of" +
                "the dependencies.";
        CreateQuestionRequest request = CreateQuestionRequest.builder()
                .withUsername(username)
                .withQuestion(question)
                .withNeedsWork(needsWork)
                .withAnswer(invalidAnswer)
                .withTags(tags)
                .build();
        when(context.getLogger()).thenReturn(lambdaLogger);

        // WHEN + THEN - handleRequest() should throw an InvalidAttributeValueException because of the INVALID answer
        assertThrows(InvalidAttributeValueException.class, () -> createQuestionActivity.handleRequest(request, context));
    }

    @Test
    void handleRequest_invalidTags_throwsInvalidAttributeValueException() {
        // GIVEN - CreateQuestionRequest with INVALID tags
        List<String> invalidTags = List.of("goo\\gle", "faceb\\ook");
        CreateQuestionRequest request = CreateQuestionRequest.builder()
                .withUsername(username)
                .withQuestion(question)
                .withNeedsWork(needsWork)
                .withAnswer(answer)
                .withTags(invalidTags)
                .build();
        when(context.getLogger()).thenReturn(lambdaLogger);

        // WHEN + THEN - handleRequest() should throw an InvalidAttributeValueException because of the INVALID tags
        assertThrows(InvalidAttributeValueException.class, () -> createQuestionActivity.handleRequest(request, context));
    }
}

package jobservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import jobservice.converters.QuestionModelConverter;
import jobservice.dynamodb.QuestionDao;
import jobservice.dynamodb.models.Question;
import jobservice.models.QuestionModel;
import jobservice.models.requests.GetAllQuestionsRequest;
import jobservice.models.results.GetAllQuestionsResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetAllQuestionsActivityTest {
    @Mock
    private QuestionDao questionDao;

    @Mock
    private QuestionModelConverter converter;

    @Mock
    private Context context;

    private LambdaLogger lambdaLogger;

    @InjectMocks
    private GetAllQuestionsActivity getAllQuestionsActivity;

    private final String username_With_Questions = "mickeymouse";
    private final String username_Without_Questions = "username-with-no-questions";

    @BeforeEach
    private void setUp() {
        initMocks(this);
        getAllQuestionsActivity = new GetAllQuestionsActivity(questionDao, converter);
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
    void handleRequest_noQuestions_returnsEmptyList() {
        // GIVEN + WHEN
        when(context.getLogger()).thenReturn(lambdaLogger);
        when(questionDao.getAllQuestionsByNeedsWork(username_Without_Questions)).thenReturn(null);
        GetAllQuestionsRequest noQuestions = GetAllQuestionsRequest.builder()
                .withUsername(username_Without_Questions)
                .build();
        GetAllQuestionsResult result = getAllQuestionsActivity.handleRequest(noQuestions, context);

        // THEN
        assertNull(result.getQuestions(),
                String.format("Expected no questions to be returned but received: %s", result.getQuestions())
        );
    }

    @Test
    void handleRequest_hasQuestions_returnsQuestions() {
        // GIVEN - a request to retrieve two Questions for the username 'mickeymouse'
        Question question1 = createQuestion("mickeymouse",
                "ruoifdg9fd",
                "How does Mockito work?",
                "It mocks the dependencies so you can test the class without relying on the functionality of" +
                        "the dependencies.",
                false,
                List.of("google", "facebook"));

        Question question2 = createQuestion("mickeymouse",
                "xyz64dgre7",
                "Is the sky blue?",
                "The sky is blue due to a phenomenon called Raleigh scattering.",
                true,
                List.of("NASA", "SpaceX"));

        List<Question> questions = List.of(question1, question2);
        QuestionModel expectedQuestion1 = new QuestionModelConverter().toQuestionModel(question1);
        QuestionModel expectedQuestion2 = new QuestionModelConverter().toQuestionModel(question2);

        GetAllQuestionsRequest mickeymouse = GetAllQuestionsRequest.builder()
                .withUsername(username_With_Questions)
                .build();

        // WHEN - call handleRequest() and mock context and questionDao
        when(context.getLogger()).thenReturn(lambdaLogger);
        when(questionDao.getAllQuestionsByNeedsWork(username_With_Questions)).thenReturn(questions);
        when(converter.toQuestionModelList(questions)).thenReturn(List.of(expectedQuestion1, expectedQuestion2));
        GetAllQuestionsResult result = getAllQuestionsActivity.handleRequest(mickeymouse, context);

        // THEN - returns the two questions given for 'mickeymouse'
        assertEquals(2, result.getQuestions().size(), "Expected to receive two questions, but received: " + result.getQuestions());
        assertTrue(result.getQuestions().contains(expectedQuestion1),
                String.format("Expected question (%s) to be contained in the results but received: %s", question1, result.getQuestions()));
        assertTrue(result.getQuestions().contains(expectedQuestion2),
                String.format("Expected question (%s) to be contained in the results but received: %s", question2, result.getQuestions()));
    }

    @Test
    void handleRequest_hasQuestions_returnsOnlyNeedsWorkQuestions() {
        // GIVEN - a request to retrieve ONLY the Questions that NEEDSWORK for the username 'mickeymouse'
        Question question1 = createQuestion("mickeymouse",
                "ruoifdg9fd",
                "How does Mockito work?",
                "It mocks the dependencies so you can test the class without relying on the functionality of" +
                        "the dependencies.",
                false,
                List.of("google", "facebook"));

        Question question2 = createQuestion("mickeymouse",
                "xyz64dgre7",
                "Is the sky blue?",
                "The sky is blue due to a phenomenon called Raleigh scattering.",
                false,
                List.of("NASA", "SpaceX"));

        Question question3 = createQuestion("mickeymouse",
                "abcduyrry0",
                "Is the sky red?",
                "The sky is red due to a phenomenon called Sunset.",
                true,
                List.of("Russians", "SpaceX"));

        List<Question> questions = new ArrayList<>(List.of(question1, question2, question3));
        List<Question> needsWorkQuestions = new ArrayList<>(List.of(question3));
        QuestionModel expectedQuestion3 = new QuestionModelConverter().toQuestionModel(question3);

        GetAllQuestionsRequest mickeymouse = GetAllQuestionsRequest.builder()
                .withUsername(username_With_Questions)
                .withOnlyNeedsWork(true)
                .build();

        // WHEN - call handleRequest() and mock context and questionDao
        when(context.getLogger()).thenReturn(lambdaLogger);
        when(questionDao.getAllQuestionsByNeedsWork(username_With_Questions)).thenReturn(questions);
        when(converter.toQuestionModelList(needsWorkQuestions)).thenReturn(List.of(expectedQuestion3));
        GetAllQuestionsResult result = getAllQuestionsActivity.handleRequest(mickeymouse, context);

        // THEN - returns only the one question given for 'mickeymouse' that needs work
        assertEquals(1, result.getQuestions().size(), "Expected to receive one question that needs work, but received: " + result.getQuestions());
        assertTrue(result.getQuestions().contains(expectedQuestion3),
                String.format("Expected question (%s) to be contained in the results but received: %s", question1, result.getQuestions()));
    }

    private Question createQuestion(String username, String questionId, String actualQuestion, String answer, Boolean needsWork, List<String> tags) {
        Question question = Question.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withQuestion(actualQuestion)
                .withAnswer(answer)
                .withNeedsWork(needsWork)
                .withTags(tags)
                .build();
        return question;
    }
}

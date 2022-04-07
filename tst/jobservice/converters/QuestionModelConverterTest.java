package jobservice.converters;

import jobservice.dynamodb.models.Question;
import jobservice.models.QuestionModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class QuestionModelConverterTest {

    private final String username = "george";
    private final String questionId = "ruoifdg9fd";
    private final String question = "How does Mockito work?";
    private final Boolean needsWork = false;
    private final String answer = "It mocks the dependencies so you can test the class without relying on the functionality of" +
            "the dependencies.";
    private final List<String> tags = List.of("google", "facebook");

    private QuestionModelConverter questionModelConverter;

    @BeforeEach
    public void setUp() {
        questionModelConverter = new QuestionModelConverter();
    }

    @Test
    void  toQuestionModel_validQuestion_convertsToQuestionModel() {
        // GIVEN - a question with valid username, questionId, question, needsWork, answer and a list of tags
        Question questionToConvert = Question.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withQuestion(question)
                .withNeedsWork(needsWork)
                .withAnswer(answer)
                .withTags(tags)
                .build();

        // WHEN - call toQuestionModel()
        QuestionModel result = questionModelConverter.toQuestionModel(questionToConvert);

        // THEN - returns a QuestionModel with all values
        assertEquals(username, result.getUsername(), "The username for the question should be: " + username);
        assertEquals(questionId, result.getQuestionId(), "The questionId for the question should be: " + questionId);
        assertEquals(question, result.getQuestion(), "The question should be: " + question);
        assertEquals(needsWork, result.getNeedsWork(), "The needsWorks for the question should be: " + needsWork);
        assertEquals(answer, result.getAnswer(), "The answer for the question should be: " + answer);
        assertEquals(tags, result.getTags(), "The tags for the question should be: " + tags);
    }

    @Test
    void  toQuestionModel_withNoAnswerAndNoTags_convertsToQuestionModelWithNoAnswerAndNoTags() {
        // GIVEN - a question with valid username, questionId, question, needsWork but no answer nor a list of tags
        Question questionToConvert = Question.builder()
                .withUsername(username)
                .withQuestionId(questionId)
                .withQuestion(question)
                .withNeedsWork(needsWork)
                .build();

        // WHEN - call toQuestionModel()
        QuestionModel result = questionModelConverter.toQuestionModel(questionToConvert);

        // THEN - returns a QuestionModel with all values except an answer or tags
        assertEquals(username, result.getUsername(), "The username for the question should be: " + username);
        assertEquals(questionId, result.getQuestionId(), "The questionId for the question should be: " + questionId);
        assertEquals(question, result.getQuestion(), "The question should be: " + question);
        assertEquals(needsWork, result.getNeedsWork(), "The needsWorks for the question should be: " + needsWork);
        assertNull(result.getAnswer(), "The answer for the question should be null");
        assertNull(result.getTags(), "The tags for the question should be null");
    }
}

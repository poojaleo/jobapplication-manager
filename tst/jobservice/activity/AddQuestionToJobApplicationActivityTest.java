package jobservice.activity;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import jobservice.converters.JobApplicationModelConverter;
import jobservice.dynamodb.JobApplicationDao;
import jobservice.dynamodb.QuestionDao;
import jobservice.dynamodb.models.JobApplication;
import jobservice.dynamodb.models.Question;
import jobservice.exceptions.JobApplicationNotFoundException;
import jobservice.exceptions.QuestionNotFoundException;
import jobservice.models.JobApplicationModel;
import jobservice.models.Status;
import jobservice.models.requests.AddQuestionToJobApplicationRequest;
import jobservice.models.results.AddQuestionToJobApplicationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AddQuestionToJobApplicationActivityTest {
    private final String usernameTest = "charles";
    private final String nonExistentUser = "doesnotexist";
    private final String nonExistentApplicationId = "badId";
    private final String jobTitleTest = "BackendDeveloper";
    private final String companyTest = "JobSmarter";
    private final Status statusTest = Status.INTERESTED;
    private final String nextReminderTest = "03/28/22";
    private final String applicationIdTest = "ab123";
    private final String questionId = "ruoifdg9fd";
    private final String questionTest = "Why jobSmarter?";
    private final String invalidQuestionId = "xyz64dgre7";
    private List<String> questionListTest = new ArrayList<>();
    private List<String> updatedQuestionList = new ArrayList<>();

    @Mock
    private JobApplicationDao jobApplicationDao;

    @Mock
    private QuestionDao questionDao;

    @Mock
    private JobApplicationModelConverter jobApplicationModelConverter;

    @Mock
    private Context context;

    private AddQuestionToJobApplicationActivity addQuestionToJobApplicationActivity;
    private LambdaLogger lambdaLogger;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        addQuestionToJobApplicationActivity = new AddQuestionToJobApplicationActivity(jobApplicationDao, questionDao, jobApplicationModelConverter);

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
    public void handleRequest_addQuestionToExistingQuestionList_returnsUpdatedJobApplication() {
        questionListTest.add("123xyz");

        JobApplication jobApplication = JobApplication.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withCompany(companyTest)
                .withStatus(statusTest)
                .withJobTitle(jobTitleTest)
                .withNextReminder(nextReminderTest)
                .withQuestionList(questionListTest)
                .build();

        Question question = Question.builder()
                .withUsername(usernameTest)
                .withQuestion(questionTest)
                .withQuestionId(questionId)
                .build();

        AddQuestionToJobApplicationRequest request = AddQuestionToJobApplicationRequest.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withQuestionId(questionId)
                .build();

        questionListTest.add(questionId);

        JobApplication savedJobApplication = JobApplication.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withCompany(companyTest)
                .withStatus(statusTest)
                .withJobTitle(jobTitleTest)
                .withNextReminder(nextReminderTest)
                .withQuestionList(questionListTest)
                .build();

        JobApplicationModel jobApplicationModel = JobApplicationModel.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withCompany(companyTest)
                .withStatus(statusTest)
                .withJobTitle(jobTitleTest)
                .withNextReminder(nextReminderTest)
                .withQuestionList(questionListTest)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(jobApplicationDao.getJobApplication(usernameTest, applicationIdTest)).thenReturn(jobApplication);
        when(questionDao.getQuestion(usernameTest, questionId)).thenReturn(question);
        when(jobApplicationDao.saveJobApplication(savedJobApplication)).thenReturn(savedJobApplication);
        when(jobApplicationModelConverter.toJobApplicationModel(savedJobApplication)).thenReturn(jobApplicationModel);

        // WHEN
        AddQuestionToJobApplicationResult result = addQuestionToJobApplicationActivity.handleRequest(request, context);

        // THEN
        assertEquals(2, result.getJobApplicationModel().getQuestionsList().size());
        assertTrue(result.getJobApplicationModel().getQuestionsList().contains(questionId), "Expected question list to contain new question");
        assertEquals(usernameTest, result.getJobApplicationModel().getUsername(), "Expected username to stay the same");
        assertEquals(jobTitleTest, result.getJobApplicationModel().getJobTitle(), "Expected jobTitle to stay the same");
        assertEquals(companyTest, result.getJobApplicationModel().getCompany(), "Expected Company to stay the same");
        assertEquals(statusTest, result.getJobApplicationModel().getStatus(), "Expected Status to stay the same");
        assertEquals(nextReminderTest, result.getJobApplicationModel().getNextReminder(), "Expected NextReminder to stay the same");
    }

    @Test
    public void handleRequest_addQuestionToNonExistentQuestionList_returnsUpdatedJobApplication() {
        List<String> newQuestions = new ArrayList<>();
        newQuestions.add(questionId);

        JobApplication jobApplication = JobApplication.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withCompany(companyTest)
                .withStatus(statusTest)
                .withJobTitle(jobTitleTest)
                .withNextReminder(nextReminderTest)
                .build();

        Question question = Question.builder()
                .withUsername(usernameTest)
                .withQuestion(questionTest)
                .withQuestionId(questionId)
                .build();

        AddQuestionToJobApplicationRequest request = AddQuestionToJobApplicationRequest.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withQuestionId(questionId)
                .build();

        JobApplication savedJobApplication = JobApplication.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withCompany(companyTest)
                .withStatus(statusTest)
                .withJobTitle(jobTitleTest)
                .withNextReminder(nextReminderTest)
                .withQuestionList(newQuestions)
                .build();

        JobApplicationModel jobApplicationModel = JobApplicationModel.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withCompany(companyTest)
                .withStatus(statusTest)
                .withJobTitle(jobTitleTest)
                .withNextReminder(nextReminderTest)
                .withQuestionList(newQuestions)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(jobApplicationDao.getJobApplication(usernameTest, applicationIdTest)).thenReturn(jobApplication);
        when(questionDao.getQuestion(usernameTest, questionId)).thenReturn(question);
        when(jobApplicationDao.saveJobApplication(savedJobApplication)).thenReturn(savedJobApplication);
        when(jobApplicationModelConverter.toJobApplicationModel(savedJobApplication)).thenReturn(jobApplicationModel);

        // WHEN
        AddQuestionToJobApplicationResult result = addQuestionToJobApplicationActivity.handleRequest(request, context);

        // THEN
        assertNotNull(result.getJobApplicationModel().getQuestionsList());
        assertTrue(result.getJobApplicationModel().getQuestionsList().contains(questionId));
        assertEquals(usernameTest, result.getJobApplicationModel().getUsername(), "Expected username to stay the same");
        assertEquals(jobTitleTest, result.getJobApplicationModel().getJobTitle(), "Expected jobTitle to stay the same");
        assertEquals(companyTest, result.getJobApplicationModel().getCompany(), "Expected Company to stay the same");
        assertEquals(statusTest, result.getJobApplicationModel().getStatus(), "Expected Status to stay the same");
        assertEquals(nextReminderTest, result.getJobApplicationModel().getNextReminder(), "Expected NextReminder to stay the same");

    }

    @Test
    public void handleRequest_withNonExistentJobApplication_throwsJobApplicationNotFoundException () {
        JobApplication jobApplication = JobApplication.builder()
                .withUsername(nonExistentUser)
                .withApplicationId(nonExistentApplicationId)
                .withCompany(companyTest)
                .withStatus(statusTest)
                .withJobTitle(jobTitleTest)
                .withNextReminder(nextReminderTest)
                .build();

        Question question = Question.builder()
                .withUsername(nonExistentUser)
                .withQuestion(questionTest)
                .withQuestionId(questionId)
                .build();

        AddQuestionToJobApplicationRequest request = AddQuestionToJobApplicationRequest.builder()
                .withUsername(nonExistentUser)
                .withApplicationId(nonExistentApplicationId)
                .withQuestionId(questionId)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(jobApplicationDao.getJobApplication(nonExistentUser, nonExistentApplicationId)).thenThrow(JobApplicationNotFoundException.class);
        when(questionDao.getQuestion(nonExistentUser, questionId)).thenReturn(question);

        // WHEN + THEN

        assertThrows(JobApplicationNotFoundException.class, ()-> {
            addQuestionToJobApplicationActivity.handleRequest(request, context);
        });

    }

    @Test
    public void handleRequest_withNonExistentQuestionId_throwsQuestionNotFoundException() {
        JobApplication jobApplication = JobApplication.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withCompany(companyTest)
                .withStatus(statusTest)
                .withJobTitle(jobTitleTest)
                .withNextReminder(nextReminderTest)
                .build();


        AddQuestionToJobApplicationRequest request = AddQuestionToJobApplicationRequest.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withQuestionId(invalidQuestionId)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(jobApplicationDao.getJobApplication(usernameTest, applicationIdTest)).thenReturn(jobApplication);
        when(questionDao.getQuestion(usernameTest, invalidQuestionId)).thenThrow(QuestionNotFoundException.class);

        // WHEN + THEN

        assertThrows(QuestionNotFoundException.class, ()-> {
            addQuestionToJobApplicationActivity.handleRequest(request, context);
        });

    }

    @Test
    public void handleRequest_withQuestionAlreadyOnList_doesNotDuplicateQuestion() {
        List<String> oldQuestions = new ArrayList<>();
        oldQuestions.add(questionId);
        List<String> savedQuestions = new ArrayList<>();
        savedQuestions.add(questionId);

        JobApplication jobApplication = JobApplication.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withCompany(companyTest)
                .withStatus(statusTest)
                .withJobTitle(jobTitleTest)
                .withNextReminder(nextReminderTest)
                .withQuestionList(oldQuestions)
                .build();

        Question question = Question.builder()
                .withUsername(usernameTest)
                .withQuestion(questionTest)
                .withQuestionId(questionId)
                .build();

        AddQuestionToJobApplicationRequest request = AddQuestionToJobApplicationRequest.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withQuestionId(questionId)
                .build();

        JobApplication savedJobApplication = JobApplication.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withCompany(companyTest)
                .withStatus(statusTest)
                .withJobTitle(jobTitleTest)
                .withNextReminder(nextReminderTest)
                .withQuestionList(savedQuestions)
                .build();

        JobApplicationModel jobApplicationModel = JobApplicationModel.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withCompany(companyTest)
                .withStatus(statusTest)
                .withJobTitle(jobTitleTest)
                .withNextReminder(nextReminderTest)
                .withQuestionList(savedQuestions)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(jobApplicationDao.getJobApplication(usernameTest, applicationIdTest)).thenReturn(jobApplication);
        when(questionDao.getQuestion(usernameTest, questionId)).thenReturn(question);
        when(jobApplicationDao.saveJobApplication(savedJobApplication)).thenReturn(savedJobApplication);
        when(jobApplicationModelConverter.toJobApplicationModel(savedJobApplication)).thenReturn(jobApplicationModel);

        // WHEN
        AddQuestionToJobApplicationResult result = addQuestionToJobApplicationActivity.handleRequest(request, context);

        // THEN
        assertEquals(usernameTest, result.getJobApplicationModel().getUsername(), "Expected username to stay the same");
        assertEquals(jobTitleTest, result.getJobApplicationModel().getJobTitle(), "Expected jobTitle to stay the same");
        assertEquals(companyTest, result.getJobApplicationModel().getCompany(), "Expected Company to stay the same");
        assertEquals(statusTest, result.getJobApplicationModel().getStatus(), "Expected Status to stay the same");
        assertEquals(nextReminderTest, result.getJobApplicationModel().getNextReminder(), "Expected NextReminder to stay the same");
        assertEquals(oldQuestions, result.getJobApplicationModel().getQuestionsList(), "Expected QuestionList not to change");

    }


}

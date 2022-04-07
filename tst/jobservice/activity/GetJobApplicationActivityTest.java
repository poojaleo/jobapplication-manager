package jobservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import jobservice.converters.JobApplicationModelConverter;
import jobservice.dynamodb.JobApplicationDao;
import jobservice.dynamodb.models.JobApplication;
import jobservice.exceptions.JobApplicationNotFoundException;
import jobservice.models.JobApplicationModel;
import jobservice.models.Status;
import jobservice.models.requests.GetJobApplicationRequest;
import jobservice.models.results.GetJobApplicationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetJobApplicationActivityTest {
    private final String usernameTest = "charles";
    private final String nonExistentUser = "doesnotexist";
    private final String nonExistentId = "badId";
    private final String applicationIdTest = "ab123";
    private final String jobTitleTest = "BackendDeveloper";
    private final String companyTest = "JobSmarter";
    private final String jobURLLink = "www.jobSmarter.com";
    private final Status statusTest = Status.INTERESTED;
    private final String nextReminderTest = "03/28/22";
    private final String notesTest = "TheseAreNotes";
    private final String locationTest = "Seattle";
    private List<String> questionListTest = new ArrayList<>();

    @Mock
    private JobApplicationDao jobApplicationDao;

    @Mock
    private JobApplicationModelConverter jobApplicationModelConverter;

    @Mock
    private Context context;

    private GetJobApplicationActivity getJobApplicationActivity;
    private LambdaLogger lambdaLogger;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        getJobApplicationActivity = new GetJobApplicationActivity(jobApplicationDao, jobApplicationModelConverter);
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
    public void handleRequest_goodRequest_returnsJobApplication() {
        // GIVEN
        questionListTest.add("123xyz");

        JobApplication jobApplication = JobApplication.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withCompany(companyTest)
                .withLocation(locationTest)
                .withStatus(statusTest)
                .withJobTitle(jobTitleTest)
                .withJobUrlLink(jobURLLink)
                .withNotes(notesTest)
                .withQuestionList(questionListTest)
                .withNextReminder(nextReminderTest)
                .build();

        JobApplicationModel jobApplicationModel = JobApplicationModel.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withCompany(companyTest)
                .withLocation(locationTest)
                .withStatus(statusTest)
                .withJobTitle(jobTitleTest)
                .withJobUrlLink(jobURLLink)
                .withNotes(notesTest)
                .withNextReminder(nextReminderTest)
                .withQuestionList(questionListTest)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(jobApplicationDao.getJobApplication(usernameTest, applicationIdTest)).thenReturn(jobApplication);
        when(jobApplicationModelConverter.toJobApplicationModel(jobApplication)).thenReturn(jobApplicationModel);

        GetJobApplicationRequest getJobApplicationRequest = GetJobApplicationRequest.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .build();
        // WHEN

        GetJobApplicationResult getJobApplicationResult = getJobApplicationActivity.handleRequest(getJobApplicationRequest, context);

        // THEN
        assertEquals(usernameTest, getJobApplicationResult.getJobApplicationModel().getUsername(), "Expected username to be: " + usernameTest);
        assertEquals(applicationIdTest, getJobApplicationResult.getJobApplicationModel().getApplicationId(), "Expected applicationId to be: " + applicationIdTest);
        assertEquals(jobTitleTest, getJobApplicationResult.getJobApplicationModel().getJobTitle(), "Expected jobTitle to be: " +jobTitleTest);
        assertEquals(companyTest, getJobApplicationResult.getJobApplicationModel().getCompany(), "Expected company to be: " +companyTest);
        assertEquals(statusTest, getJobApplicationResult.getJobApplicationModel().getStatus(), "Expected status to be: " + statusTest);
        assertEquals(notesTest, getJobApplicationResult.getJobApplicationModel().getNotes(), "Expected Notes to be: "+ notesTest);
        assertEquals(jobURLLink, getJobApplicationResult.getJobApplicationModel().getJobUrlLink(), "Expected JobURLLink to be: " + jobURLLink);
        assertEquals(locationTest, getJobApplicationResult.getJobApplicationModel().getLocation(), "Expected location to be " + locationTest);
        assertEquals(questionListTest, getJobApplicationResult.getJobApplicationModel().getQuestionsList(), "Expected questionList to be :" + questionListTest);
    }

    @Test
    public void handleRequest_requestWithNonExistentIDs_throwsException() {
        // GIVEN
        GetJobApplicationRequest getJobApplicationRequest = GetJobApplicationRequest.builder()
                .withUsername(nonExistentUser)
                .withApplicationId(nonExistentId)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(jobApplicationDao.getJobApplication(nonExistentUser, nonExistentId)).thenThrow(JobApplicationNotFoundException.class);

        // WHEN + THEN

        assertThrows(JobApplicationNotFoundException.class, () -> getJobApplicationActivity.handleRequest(getJobApplicationRequest, context));

    }


}

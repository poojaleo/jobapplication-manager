package jobservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import jobservice.converters.JobApplicationModelConverter;
import jobservice.dynamodb.JobApplicationDao;
import jobservice.dynamodb.models.JobApplication;
import jobservice.exceptions.JobApplicationNotFoundException;
import jobservice.models.JobApplicationModel;
import jobservice.models.Status;
import jobservice.models.requests.DeleteJobApplicationRequest;
import jobservice.models.results.DeleteJobApplicationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class DeleteJobApplicationActivityTest {
    private final String usernameTest = "charles";
    private final String nonExistentUser = "doesnotexist";
    private final String nonExistentId = "badId";
    private final String applicationIdTest = "ab123";
    private final String jobTitleTest = "BackendDeveloper";
    private final String jobURLTest = "www.jobsmarter.com";
    private final String companyTest = "JobSmarter";
    private final Status statusTest = Status.INTERESTED;
    private final String notesTest = "TheseAreNotes";
    private final String locationTest = "Seattle";
    private List<String> questionListTest = new ArrayList<String>();

    @Mock
    private JobApplicationDao jobApplicationDao;

    @Mock
    private JobApplicationModelConverter jobApplicationModelConverter;

    @Mock
    private Context context;

    private DeleteJobApplicationActivity deleteJobApplicationActivity;
    private LambdaLogger lambdaLogger;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        deleteJobApplicationActivity = new DeleteJobApplicationActivity(jobApplicationDao, jobApplicationModelConverter);
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
        questionListTest.add("q1");
        questionListTest.add("q2");
    }

    @Test
    public void handleRequest_jobApplicationInDataBase_deletesJobApplication() {
        // GIVEN
        JobApplication jobApplication = JobApplication.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withCompany(companyTest)
                .withJobTitle(jobTitleTest)
                .withJobUrlLink(jobURLTest)
                .withStatus(statusTest)
                .withNotes(notesTest)
                .withLocation(locationTest)
                .withQuestionList(questionListTest)
                .build();

        JobApplicationModel jobApplicationModel = JobApplicationModel.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withCompany(companyTest)
                .withJobTitle(jobTitleTest)
                .withJobUrlLink(jobURLTest)
                .withStatus(statusTest)
                .withNotes(notesTest)
                .withLocation(locationTest)
                .withQuestionList(questionListTest)
                .build();

        DeleteJobApplicationRequest deleteJobApplicationRequest = DeleteJobApplicationRequest.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(jobApplicationDao.deleteJobApplication(usernameTest, applicationIdTest)).thenReturn(jobApplication);
        when(jobApplicationModelConverter.toJobApplicationModel(jobApplication)).thenReturn(jobApplicationModel);

        // WHEN
        DeleteJobApplicationResult result = deleteJobApplicationActivity.handleRequest(deleteJobApplicationRequest, context);

        // THEN
        verify(jobApplicationDao).deleteJobApplication(usernameTest, applicationIdTest);
        assertEquals(usernameTest, result.getJobApplicationModel().getUsername(), "Expected username to be: " + usernameTest);
        assertEquals(applicationIdTest, result.getJobApplicationModel().getApplicationId(), "Expected applicationId to be: " + applicationIdTest);
        assertEquals(jobTitleTest, result.getJobApplicationModel().getJobTitle(), "Expected jobTitle to be: " +jobTitleTest);
        assertEquals(companyTest, result.getJobApplicationModel().getCompany(), "Expected company to be: " +companyTest);
        assertEquals(notesTest, result.getJobApplicationModel().getNotes(), "Expected notes to be: " + notesTest);
        assertEquals(statusTest, result.getJobApplicationModel().getStatus(), "Expected status to be: " + statusTest);
    }

    @Test
    public void handleRequest_jobApplicationNotInDatabase_throwsException() {
        // GIVEN
        DeleteJobApplicationRequest deleteJobApplicationRequest = DeleteJobApplicationRequest.builder()
                .withUsername(nonExistentUser)
                .withApplicationId(nonExistentId)
                .build();
        when(jobApplicationDao.deleteJobApplication(nonExistentUser, nonExistentId)).thenThrow(JobApplicationNotFoundException.class);
        when(context.getLogger()).thenReturn(lambdaLogger);

        assertThrows(JobApplicationNotFoundException.class, () -> {
            deleteJobApplicationActivity.handleRequest(deleteJobApplicationRequest, context);
        });

    }

}

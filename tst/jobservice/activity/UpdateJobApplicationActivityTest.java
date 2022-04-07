package jobservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import jobservice.converters.JobApplicationModelConverter;
import jobservice.dynamodb.JobApplicationDao;
import jobservice.dynamodb.models.JobApplication;
import jobservice.models.JobApplicationModel;
import jobservice.models.Status;
import jobservice.models.requests.UpdateJobApplicationRequest;
import jobservice.models.results.UpdateJobApplicationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class UpdateJobApplicationActivityTest {
    private final String usernameTest = "charles";
    private final String nonExistentUser = "doesnotexist";
    private final String nonExistentId = "badId";
    private final String applicationIdTest = "ab123";
    private final String jobTitleTest = "BackendDeveloper";
    private final String updateJobTitleTest = "BackendDeveloper2";
    private final String jobURLLink = "www.jobSmarter.com";
    private final String companyTest = "JobSmarter";
    private final Status statusTest = Status.INTERESTED;
    private final String nextReminderTest = "03/28/22";
    private final String notesTest = "TheseAreNotes";
    private final String updatedNotesTest = "TheseAreAlsoNotes";
    private final String locationTest = "Seattle";
    private List<String> questionListTest = new ArrayList<String>();

    @Mock
    private JobApplicationDao jobApplicationDao;

    @Mock
    private JobApplicationModelConverter jobApplicationModelConverter;

    @Mock
    private Context context;

    private UpdateJobApplicationActivity updateJobApplicationActivity;
    private LambdaLogger lambdaLogger;


    @BeforeEach
    public void setUp() {
        initMocks(this);
        updateJobApplicationActivity = new UpdateJobApplicationActivity(jobApplicationDao, jobApplicationModelConverter);

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
    public void handleRequest_updateJobTitle_returnsUpdatedJobApplicationResult() {
        questionListTest.add("123xyz");
        //GIVEN
        JobApplication jobApplication = JobApplication.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withJobTitle(jobTitleTest)
                .withJobUrlLink(jobURLLink)
                .withNotes(notesTest)
                .withQuestionList(questionListTest)
                .withNextReminder(nextReminderTest)
                .withCompany(companyTest)
                .withStatus(statusTest)
                .build();

        UpdateJobApplicationRequest updateJobApplicationRequest = UpdateJobApplicationRequest.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withJobTitle(updateJobTitleTest)
                .withNotes(updatedNotesTest)
                .build();

        JobApplication savedJobApplication = JobApplication.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withJobTitle(updateJobTitleTest)
                .withJobUrlLink(jobURLLink)
                .withNotes(updatedNotesTest)
                .withQuestionList(questionListTest)
                .withNextReminder(nextReminderTest)
                .withCompany(companyTest)
                .withStatus(statusTest)
                .build();


        JobApplicationModel jobApplicationModel = JobApplicationModel.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withJobTitle(updateJobTitleTest)
                .withJobUrlLink(jobURLLink)
                .withNotes(updatedNotesTest)
                .withQuestionList(questionListTest)
                .withNextReminder(nextReminderTest)
                .withCompany(companyTest)
                .withStatus(statusTest)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(jobApplicationDao.getJobApplication(usernameTest, applicationIdTest)).thenReturn(jobApplication);
        when(jobApplicationDao.saveJobApplication(savedJobApplication)).thenReturn(savedJobApplication);
        when(jobApplicationModelConverter.toJobApplicationModel(savedJobApplication)).thenReturn(jobApplicationModel);

        // WHEN

        UpdateJobApplicationResult updateJobApplicationResult = updateJobApplicationActivity.handleRequest(updateJobApplicationRequest, context);

        // THEN
        assertEquals(usernameTest, updateJobApplicationResult.getJobApplicationModel().getUsername(), "Expected username to be" + usernameTest);
        assertEquals(companyTest, updateJobApplicationResult.getJobApplicationModel().getCompany(), "Expected company to be: " + companyTest);
        assertEquals(updateJobTitleTest, updateJobApplicationResult.getJobApplicationModel().getJobTitle(), "Expected jobTitle to be " +updateJobTitleTest);
        assertEquals(updatedNotesTest, updateJobApplicationResult.getJobApplicationModel().getNotes(), "Expected notes to be: " + updatedNotesTest);
        assertEquals(statusTest, updateJobApplicationResult.getJobApplicationModel().getStatus(), "Expected Status to be: " + statusTest);
        assertEquals(applicationIdTest, updateJobApplicationResult.getJobApplicationModel().getApplicationId(), "Expected applicationId to be: " + applicationIdTest);
    }

    @Test
    public void handleRequest_withNonRequiredFieldPreviouslyNull_returnsUpdatedApplicationResult() {
        questionListTest.add("123xyz");
        //GIVEN
        JobApplication jobApplication = JobApplication.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withStatus(statusTest)
                .withJobTitle(jobTitleTest)
                .withCompany(companyTest)
                .build();


        UpdateJobApplicationRequest updateJobApplicationRequest = UpdateJobApplicationRequest.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withNotes(updatedNotesTest)
                .withJobTitle(updateJobTitleTest)
                .withQuestionList(questionListTest)
                .build();

        JobApplication savedJobApplication = JobApplication.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withJobTitle(updateJobTitleTest)
                .withCompany(companyTest)
                .withNotes(updatedNotesTest)
                .withStatus(statusTest)
                .withQuestionList(questionListTest)
                .build();


        JobApplicationModel jobApplicationModel = JobApplicationModel.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withJobTitle(updateJobTitleTest)
                .withCompany(companyTest)
                .withNotes(updatedNotesTest)
                .withStatus(statusTest)
                .withQuestionList(questionListTest)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(jobApplicationDao.getJobApplication(usernameTest, applicationIdTest)).thenReturn(jobApplication);
        when(jobApplicationDao.saveJobApplication(savedJobApplication)).thenReturn(savedJobApplication);
        when(jobApplicationModelConverter.toJobApplicationModel(savedJobApplication)).thenReturn(jobApplicationModel);

        // WHEN

        UpdateJobApplicationResult updateJobApplicationResult = updateJobApplicationActivity.handleRequest(updateJobApplicationRequest, context);

        // THEN
        assertEquals(usernameTest, updateJobApplicationResult.getJobApplicationModel().getUsername(), "Expected username to be" + usernameTest);
        assertEquals(companyTest, updateJobApplicationResult.getJobApplicationModel().getCompany(), "Expected company to be: " + companyTest);
        assertEquals(updateJobTitleTest, updateJobApplicationResult.getJobApplicationModel().getJobTitle(), "Expected jobTitle to be " +updateJobTitleTest);
        assertEquals(statusTest, updateJobApplicationResult.getJobApplicationModel().getStatus(), "Expected Status to be: " + statusTest);
        assertEquals(applicationIdTest, updateJobApplicationResult.getJobApplicationModel().getApplicationId(), "Expected applicationId to be: " + applicationIdTest);
        assertEquals(updatedNotesTest, updateJobApplicationResult.getJobApplicationModel().getNotes(), "Expected Notes be: " +updatedNotesTest);
    }

}

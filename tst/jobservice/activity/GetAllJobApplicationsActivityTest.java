package jobservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import jobservice.converters.JobApplicationModelConverter;
import jobservice.dynamodb.JobApplicationDao;
import jobservice.dynamodb.models.JobApplication;
import jobservice.exceptions.EmptyJobApplicationListException;
import jobservice.exceptions.JobApplicationNotFoundException;
import jobservice.models.JobApplicationModel;
import jobservice.models.Status;
import jobservice.models.requests.GetAllJobApplicationsRequest;
import jobservice.models.results.GetAllJobApplicationsResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetAllJobApplicationsActivityTest {
    private final String usernameTest = "charles";
    private final String anotherApplicationId = "345cd";
    private final String applicationIdTest = "ab123";
    private final String jobTitleTest = "BackendDeveloper";
    private final String jobURLTest = "www.jobsmarter.com";
    private final String companyTest = "JobSmarter";
    private final Status statusTest = Status.INTERESTED;
    private final Status anotherStatus = Status.APPLIED;
    private final String nextReminderTest = "03/28/22";
    private final String notesTest = "TheseAreNotes";
    private final String locationTest = "Seattle";
    private List<String> questionListTest = new ArrayList<String>();

    @Mock
    private JobApplicationDao jobApplicationDao;

    @Mock
    private JobApplicationModelConverter jobApplicationModelConverter;

    @Mock
    Context context;

    private GetAllJobApplicationsActivity getAllJobApplicationsActivity;
    private LambdaLogger lambdaLogger;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        getAllJobApplicationsActivity = new GetAllJobApplicationsActivity(jobApplicationDao, jobApplicationModelConverter);
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
    public void handleRequest_userHasTwoApplications_returnsTwoApplications() {
        // GIVEN

        JobApplication jobApplication = JobApplication.builder()
                .withUsername(usernameTest)
                .withApplicationId(applicationIdTest)
                .withCompany(companyTest)
                .withJobTitle(jobTitleTest)
                .withJobUrlLink(jobURLTest)
                .withStatus(statusTest)
                .withNotes(notesTest)
                .withNextReminder(nextReminderTest)
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
                .withNextReminder(nextReminderTest)
                .withLocation(locationTest)
                .withQuestionList(questionListTest)
                .build();

        JobApplication anotherJobApplication = JobApplication.builder()
                .withUsername(usernameTest)
                .withApplicationId(anotherApplicationId)
                .withCompany(companyTest)
                .withJobTitle(jobTitleTest)
                .withJobUrlLink(jobURLTest)
                .withStatus(anotherStatus)
                .withNotes(notesTest)
                .withLocation(locationTest)
                .withQuestionList(questionListTest)
                .build();

        JobApplicationModel anotherJobApplicationModel = JobApplicationModel.builder()
                .withUsername(usernameTest)
                .withApplicationId(anotherApplicationId)
                .withCompany(companyTest)
                .withJobTitle(jobTitleTest)
                .withJobUrlLink(jobURLTest)
                .withStatus(anotherStatus)
                .withNotes(notesTest)
                .withLocation(locationTest)
                .withQuestionList(questionListTest)
                .build();

        List<JobApplication> jobList = new ArrayList<>();
        jobList.add(jobApplication);
        jobList.add(anotherJobApplication);

        GetAllJobApplicationsRequest getAllJobApplicationsRequest = GetAllJobApplicationsRequest.builder()
                .withUsername(usernameTest)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(jobApplicationDao.getAllJobApplications(usernameTest)).thenReturn(jobList);
        when(jobApplicationModelConverter.toJobApplicationModel(jobApplication)).thenReturn(jobApplicationModel);
        when(jobApplicationModelConverter.toJobApplicationModel(anotherJobApplication)).thenReturn(anotherJobApplicationModel);

        // WHEN
        GetAllJobApplicationsResult result = getAllJobApplicationsActivity.handleRequest(getAllJobApplicationsRequest, context);

        // THEN
        verify(jobApplicationDao).getAllJobApplications(usernameTest);
        assertEquals(2, result.getJobApplicationList().size(), "Expected one job application to be returned");
    }

    @Test
    public void handleRequest_userHasOneApplication_returnsOneApplication() {
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

        List<JobApplication> jobList = new ArrayList<>();
        jobList.add(jobApplication);

        GetAllJobApplicationsRequest getAllJobApplicationsRequest = GetAllJobApplicationsRequest.builder()
                .withUsername(usernameTest)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(jobApplicationDao.getAllJobApplications(usernameTest)).thenReturn(jobList);
        when(jobApplicationModelConverter.toJobApplicationModel(jobApplication)).thenReturn(jobApplicationModel);

        // WHEN
        GetAllJobApplicationsResult result = getAllJobApplicationsActivity.handleRequest(getAllJobApplicationsRequest, context);

        // THEN
        verify(jobApplicationDao).getAllJobApplications(usernameTest);
        assertEquals(1, result.getJobApplicationList().size(), "Expected one job application to be returned");
    }

    @Test
    public void handleRequest_userHasNoApplications_returnsNull() {
        GetAllJobApplicationsRequest getAllJobApplicationsRequest = GetAllJobApplicationsRequest.builder()
                .withUsername(usernameTest)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(jobApplicationDao.getAllJobApplications(usernameTest)).thenReturn(null);

        GetAllJobApplicationsResult result = getAllJobApplicationsActivity.handleRequest(getAllJobApplicationsRequest, context);
        // WHEN + THEN
        assertEquals(0, result.getJobApplicationList().size(), "Expected no applications to be returned");
    }


}

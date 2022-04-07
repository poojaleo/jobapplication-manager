package jobservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import jobservice.converters.JobApplicationModelConverter;
import jobservice.dynamodb.JobApplicationDao;
import jobservice.dynamodb.models.JobApplication;
import jobservice.exceptions.InvalidAttributeValueException;
import jobservice.exceptions.JobApplicationNotFoundException;
import jobservice.models.JobApplicationModel;
import jobservice.models.Status;
import jobservice.models.requests.CreateJobApplicationRequest;
import jobservice.models.results.CreateJobApplicationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreateJobApplicationActivityTest {
    private final String usernameTest = "charles";
    private final String applicationIdTest = "ab123";
    private final String jobTitleTest = "BackendDeveloper";
    private final String invalidUsernameTest = "not\\valid";
    private final String companyTest = "JobSmarter";
    private final String jobURLLink = "www.jobSmarter.com";
    private final Status statusTest = Status.INTERESTED;
    private final String nextReminderTest = "03/28/22";
    private final String notesTest = "TheseAreNotes";
    private final String locationTest = "Seattle";

    @Mock
    private JobApplicationDao jobApplicationDao;

    @Mock
    private JobApplicationModelConverter jobApplicationModelConverter;

    @Mock
    private Context context;

    private CreateJobApplicationActivity createJobApplicationActivity;
    private LambdaLogger lambdaLogger;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        createJobApplicationActivity = new CreateJobApplicationActivity(jobApplicationDao, jobApplicationModelConverter);
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
            public void handleRequest_createJobApplicationWithAllRequiredAttributes_returnsCreateJobApplicationResult(){

                CreateJobApplicationRequest createJobApplicationRequest = CreateJobApplicationRequest.builder()
                        .withUsername(usernameTest)
                        .withCompany(companyTest)
                        .withJobTitle(jobTitleTest)
                        .withJobUrlLink(jobURLLink)
                        .withStatus(statusTest)
                        .withNotes(notesTest)
                        .withNextReminder(nextReminderTest)
                        .withLocation(locationTest)
                        .build();

                JobApplicationModel jobApplicationModel = JobApplicationModel.builder()
                        .withUsername(usernameTest)
                        .withCompany(companyTest)
                        .withJobTitle(jobTitleTest)
                        .withJobUrlLink(jobURLLink)
                        .withStatus(statusTest)
                        .withNotes(notesTest)
                        .withNextReminder(nextReminderTest)
                        .withLocation(locationTest)
                        .build();

                when(jobApplicationDao.saveJobApplication(any(JobApplication.class))).then(AdditionalAnswers.returnsFirstArg());
                when(context.getLogger()).thenReturn(lambdaLogger);
                when(jobApplicationModelConverter.toJobApplicationModel(any(JobApplication.class))).thenReturn(jobApplicationModel);
                when(jobApplicationDao.idAvailable(any(String.class), any(String.class))).thenReturn(true);

                CreateJobApplicationResult createJobApplicationResult = createJobApplicationActivity.handleRequest(createJobApplicationRequest, context);


                assertEquals(usernameTest, createJobApplicationResult.getJobApplicationModel().getUsername(), "Expected username to be: " + usernameTest);
                assertEquals(jobTitleTest, createJobApplicationResult.getJobApplicationModel().getJobTitle(), "Expected jobTitle to be: " +jobTitleTest);
                assertEquals(companyTest, createJobApplicationResult.getJobApplicationModel().getCompany(), "Expected company to be: " +companyTest);
                assertEquals(statusTest, createJobApplicationResult.getJobApplicationModel().getStatus(), "Expected status to be: " + statusTest);
                assertEquals(notesTest, createJobApplicationResult.getJobApplicationModel().getNotes(), "Expected Notes to be: "+ notesTest);
                assertEquals(jobURLLink, createJobApplicationResult.getJobApplicationModel().getJobUrlLink(), "Expected JobURLLink to be: " + jobURLLink);
                assertEquals(locationTest, createJobApplicationResult.getJobApplicationModel().getLocation(), "Expected location to be " + locationTest);
            }


         @Test
        public void handleRequest_withInvalidUserName_throwsException() {
            // GIVEN
            CreateJobApplicationRequest createJobApplicationRequest = CreateJobApplicationRequest.builder()
                    .withUsername(invalidUsernameTest)
                    .withCompany(companyTest)
                    .withJobTitle(jobTitleTest)
                    .withStatus(statusTest)
                    .withNextReminder(nextReminderTest)
                    .build();

            when(context.getLogger()).thenReturn(lambdaLogger);
            when(jobApplicationDao.idAvailable(any(String.class), any(String.class))).thenReturn(true);

             // WHEN + THEN
             assertThrows(InvalidAttributeValueException.class, () -> {
                 createJobApplicationActivity.handleRequest(createJobApplicationRequest, context);
             });


         }




}

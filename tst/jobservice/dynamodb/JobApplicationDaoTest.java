package jobservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import jobservice.dynamodb.models.JobApplication;
import jobservice.exceptions.JobApplicationNotFoundException;
import jobservice.models.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.when;

public class JobApplicationDaoTest {

    private final String usernameTest = "charles";
    private final String nonExistentUser = "doesnotexist";
    private final String nonExistentId = "badId";
    private final String applicationIdTest = "ab123";
    private final String jobTitleTest = "BackendDeveloper";
    private final String companyTest = "JobSmarter";
    private final Status statusTest = Status.INTERESTED;
    private final String createdAtTest = "0324";


    private JobApplicationDao jobApplicationDao;

    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @BeforeEach
    private void setup() {
        initMocks(this);
        jobApplicationDao = new JobApplicationDao(dynamoDBMapper);
    }

    @Test
    void saveJobApplication_withValidAttributes_returnsJobApplication() {
        // GIVEN
        JobApplication jobApplication = new JobApplication();
        jobApplication.setUsername(usernameTest);
        jobApplication.setApplicationId(applicationIdTest);
        jobApplication.setJobTitle(jobTitleTest);
        jobApplication.setCompany(companyTest);
        jobApplication.setStatus(statusTest);


    }

    @Test
    void getJobApplication_withValidKeys_returnsJobApplication() {
        // GIVEN
        JobApplication jobApplication = new JobApplication();
        jobApplication.setUsername(usernameTest);
        jobApplication.setApplicationId(applicationIdTest);
        jobApplication.setJobTitle(jobTitleTest);
        jobApplication.setCompany(companyTest);
        jobApplication.setStatus(statusTest);
        when(dynamoDBMapper.load(JobApplication.class, usernameTest, applicationIdTest)).thenReturn(jobApplication);

        // WHEN
        JobApplication returnedApplication = jobApplicationDao.getJobApplication(usernameTest, applicationIdTest);

        // THEN
        assertEquals(usernameTest, returnedApplication.getUsername(), "Expected username to be " + usernameTest) ;
        assertEquals(applicationIdTest, returnedApplication.getApplicationId(), "Expected applicationId to be " + applicationIdTest);
        assertEquals(jobTitleTest, returnedApplication.getJobTitle(), "Expected jobTitle to be " + jobTitleTest);
        assertEquals(statusTest, returnedApplication.getStatus(), "Expected status to be " + statusTest);
    }

    @Test
    void getJobApplication_withInvalidKeys_throwsException() {
        JobApplication jobApplication = new JobApplication();
        jobApplication.setUsername(nonExistentUser);
        jobApplication.setApplicationId(nonExistentId);
        jobApplication.setJobTitle(jobTitleTest);
        jobApplication.setCompany(companyTest);
        jobApplication.setStatus(statusTest);

        // WHEN
        when(dynamoDBMapper.load(JobApplication.class, nonExistentUser, nonExistentId)).thenReturn(null);

        // THEN
        assertThrows(JobApplicationNotFoundException.class, ()-> jobApplicationDao.getJobApplication(nonExistentUser, nonExistentId));

    }

}

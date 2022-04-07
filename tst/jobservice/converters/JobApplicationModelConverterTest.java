package jobservice.converters;

import jobservice.dynamodb.models.JobApplication;
import jobservice.models.JobApplicationModel;
import jobservice.models.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JobApplicationModelConverterTest {

    private final String usernameTest = "charles";
    private final String applicationIdTest = "ab123";
    private final String jobTitleTest = "BackendDeveloper";
    private final String companyTest = "JobSmarter";
    private final Status statusTest = Status.INTERESTED;
    private final String createdAtTest = "0324";

    private JobApplicationModelConverter jobApplicationModelConverter;

    @BeforeEach
    public void setup() {jobApplicationModelConverter = new JobApplicationModelConverter();}

    @Test
    public void toJobApplicationModel_withMinimumCredentials_returnsJobApplicationModel() {
        // GIVEN
        JobApplication jobApplication = new JobApplication();
        jobApplication.setUsername(usernameTest);
        jobApplication.setApplicationId(applicationIdTest);
        jobApplication.setJobTitle(jobTitleTest);
        jobApplication.setCompany(companyTest);
        jobApplication.setStatus(statusTest);

        // WHEN
        JobApplicationModel jobApplicationModel = jobApplicationModelConverter.toJobApplicationModel(jobApplication);

        // THEN
        assertEquals(usernameTest, jobApplicationModel.getUsername(), "Expected username to be " + usernameTest) ;
        assertEquals(applicationIdTest, jobApplicationModel.getApplicationId(), "Expected applicationId to be " + applicationIdTest);
        assertEquals(jobTitleTest, jobApplicationModel.getJobTitle(), "Expected jobTitle to be " + jobTitleTest);
        assertEquals(statusTest, jobApplicationModel.getStatus(), "Expected status to be " + statusTest);
    }
}

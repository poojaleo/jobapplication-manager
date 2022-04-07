package jobservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.converters.JobApplicationModelConverter;
import jobservice.dynamodb.JobApplicationDao;
import jobservice.dynamodb.models.JobApplication;
import jobservice.exceptions.InvalidAttributeValueException;
import jobservice.models.JobApplicationModel;
import jobservice.models.requests.CreateJobApplicationRequest;
import jobservice.models.results.CreateJobApplicationResult;
import jobservice.util.JobTrackerServiceUtility;

import javax.inject.Inject;


/**
 * Implementation of the CreateJobApplicationActivity for the JobService's CreateJobApplication API
 *
 * This API allows the customer to create a new JobApplication
 */
public class CreateJobApplicationActivity implements RequestHandler<CreateJobApplicationRequest, CreateJobApplicationResult> {
    private final JobApplicationDao jobApplicationDao;
    private final JobApplicationModelConverter jobApplicationModelConverter;

    /**
     * Instantiates a new CreateJobApplicationActivity Object
     * @param jobApplicationDao to access the JobApplication Table
     * @param jobApplicationModelConverter to convert to JobApplication Model
     */
    @Inject
    public CreateJobApplicationActivity(JobApplicationDao jobApplicationDao, JobApplicationModelConverter jobApplicationModelConverter){
        this.jobApplicationDao = jobApplicationDao;
        this.jobApplicationModelConverter = jobApplicationModelConverter;
    }

    /**
     * This method handles the incoming request by creating a new JobApplication
     * It then returns the new JobApplication
     * @param createJobApplicationRequest request object containing the username, jobTitle, company, status
     *                                    and optional location, jobUrlLink, nextReminder, notes
     * @param context lambda function context
     * @return createJobApplicationResult result object containing the API defined {@link JobApplicationModel}
     * @throws InvalidAttributeValueException if provided username or jobTitle contain invalid characters
     */
    @Override
    public CreateJobApplicationResult handleRequest(CreateJobApplicationRequest createJobApplicationRequest, Context context) throws InvalidAttributeValueException {
       LambdaLogger lambdaLogger = context.getLogger();
       lambdaLogger.log("Received Create Job Application Request for user :" + createJobApplicationRequest.getUsername());

        try{
            checkValidity(createJobApplicationRequest.getUsername());
            checkValidity(createJobApplicationRequest.getJobTitle());
        } catch (InvalidAttributeValueException e){
            throw new InvalidAttributeValueException(e.getMessage());
        }

        String generatedId = JobTrackerServiceUtility.generateId();

        while(!jobApplicationDao.idAvailable(createJobApplicationRequest.getUsername(), generatedId)){
            generatedId = JobTrackerServiceUtility.generateId();
        }


        JobApplication jobApplication = new JobApplication();
        jobApplication.setUsername(createJobApplicationRequest.getUsername());
        jobApplication.setApplicationId(generatedId);
        jobApplication.setJobTitle(createJobApplicationRequest.getJobTitle());
        jobApplication.setStatus(createJobApplicationRequest.getStatus());
        jobApplication.setCompany(createJobApplicationRequest.getCompany());

        if(createJobApplicationRequest.getLocation() != null) {
            jobApplication.setLocation(createJobApplicationRequest.getLocation());
        }

        if(createJobApplicationRequest.getNextReminder() != null) {
            jobApplication.setNextReminder(createJobApplicationRequest.getNextReminder());
        }

        if(createJobApplicationRequest.getNotes() != null) {
            jobApplication.setNotes(createJobApplicationRequest.getNotes());
        }

        if(createJobApplicationRequest.getJobUrlLink() != null) {
            jobApplication.setJobUrlLink(createJobApplicationRequest.getJobUrlLink());
        }


        JobApplication jobApplicationSaved = jobApplicationDao.saveJobApplication(jobApplication);
        JobApplicationModel jobApplicationModel = jobApplicationModelConverter.toJobApplicationModel(jobApplicationSaved);

        return CreateJobApplicationResult.builder()
                .withJobApplication(jobApplicationModel)
                .build();
    }

    /**
     * Checks if it is a valid string
     * @param stringToCheck the string to check
     * @throws InvalidAttributeValueException if the string contains invalid characters
     */
    private void checkValidity(String stringToCheck) throws InvalidAttributeValueException {
        boolean isValid = JobTrackerServiceUtility.isValidString(stringToCheck);

        if (!isValid) {
            throw new InvalidAttributeValueException(String.format("Invalid attribute: %s", stringToCheck));
        }
    }
}

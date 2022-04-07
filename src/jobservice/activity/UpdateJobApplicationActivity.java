package jobservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.converters.JobApplicationModelConverter;
import jobservice.dynamodb.JobApplicationDao;
import jobservice.dynamodb.models.JobApplication;
import jobservice.exceptions.InvalidAttributeValueException;
import jobservice.exceptions.JobApplicationNotFoundException;
import jobservice.models.JobApplicationModel;
import jobservice.models.requests.UpdateJobApplicationRequest;
import jobservice.models.results.UpdateJobApplicationResult;
import jobservice.util.JobTrackerServiceUtility;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/**
 * Implementation of the UpdateJobApplicationActivity for the JobService's UpdateJobApplication API
 *
 * This API allows the customer to update one of their saved JobApplications
 */
public class UpdateJobApplicationActivity implements RequestHandler<UpdateJobApplicationRequest, UpdateJobApplicationResult> {
    private final JobApplicationDao jobApplicationDao;
    private JobApplicationModelConverter jobApplicationModelConverter;

    /**
     * Instantiates new UpdateJobApplicationActivity Object
     * @param jobApplicationDao to access the JobApplication table
     * @param jobApplicationModelConverter to convert to JobApplication model
     */
    @Inject
    public UpdateJobApplicationActivity(JobApplicationDao jobApplicationDao, JobApplicationModelConverter jobApplicationModelConverter) {
        this.jobApplicationDao = jobApplicationDao;
        this.jobApplicationModelConverter = jobApplicationModelConverter;
    }

    /**
     * This method handles the incoming request by updating the existing JobApplication
     * with the provided JobApplication details.
     *
     * @param updateJobApplicationRequest request object containing username, applicationId, and
     *                                    optional other attributes to change.
     * @param context lambda function context
     * @return updateJobApplicationResult result object containing the API defined {@link JobApplicationModel}
     * @throws InvalidAttributeValueException if the updated JobTitle contains invalid characters
     * @throws JobApplicationNotFoundException if the provided JobApplication does not exist
     */
    @Override
    public UpdateJobApplicationResult handleRequest(UpdateJobApplicationRequest updateJobApplicationRequest, Context context) throws InvalidAttributeValueException, JobApplicationNotFoundException {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log("Received Update Job Application Request for applicationId: " +updateJobApplicationRequest.getApplicationId());

        JobApplication jobApplication;

        try {
            jobApplication = jobApplicationDao.getJobApplication(updateJobApplicationRequest.getUsername(), updateJobApplicationRequest.getApplicationId());
        } catch (JobApplicationNotFoundException e) {
            throw e;
        }

        if(updateJobApplicationRequest.getCompany() != null && !updateJobApplicationRequest.getCompany().equals("") && !jobApplication.getCompany().equals(updateJobApplicationRequest.getCompany())) {
            jobApplication.setCompany(updateJobApplicationRequest.getCompany());
        }

        if(updateJobApplicationRequest.getJobTitle() != null && !updateJobApplicationRequest.getJobTitle().equals("") && !updateJobApplicationRequest.getJobTitle().equals(jobApplication.getJobTitle())) {
            try {
                checkValidity(updateJobApplicationRequest.getJobTitle());
            } catch (InvalidAttributeValueException e) {
                throw e;
            }
            jobApplication.setJobTitle(updateJobApplicationRequest.getJobTitle());
        }

        if(updateJobApplicationRequest.getLocation() != null && !updateJobApplicationRequest.getLocation().equals("")) {
            jobApplication.setLocation(updateJobApplicationRequest.getLocation());
        }

        if(updateJobApplicationRequest.getStatus() != null && !updateJobApplicationRequest.getStatus().equals("")) {
            jobApplication.setStatus(updateJobApplicationRequest.getStatus());
        }

        if(updateJobApplicationRequest.getNextReminder() != null && !updateJobApplicationRequest.getNextReminder().equals("")) {
            jobApplication.setNextReminder(updateJobApplicationRequest.getNextReminder());
        }

        if(updateJobApplicationRequest.getNotes() != null && !updateJobApplicationRequest.getNotes().equals("")) {
            jobApplication.setNotes(updateJobApplicationRequest.getNotes());
        }

        if(updateJobApplicationRequest.getJobUrlLink() != null && !updateJobApplicationRequest.getJobUrlLink().equals("")) {
            jobApplication.setJobUrlLink(updateJobApplicationRequest.getJobUrlLink());
        }

            if (updateJobApplicationRequest.getQuestionsList() == null || updateJobApplicationRequest.getQuestionsList().isEmpty() || updateJobApplicationRequest.getQuestionsList().equals("")) {

            } else {
                jobApplication.setQuestionsList(new ArrayList<>(updateJobApplicationRequest.getQuestionsList()));
            }

        JobApplication jobApplicationSaved = jobApplicationDao.saveJobApplication(jobApplication);
        JobApplicationModel jobApplicationModel = jobApplicationModelConverter.toJobApplicationModel(jobApplicationSaved);

        return UpdateJobApplicationResult.builder()
                .withJobApplication(jobApplicationModel)
                .build();

    }

    /**
     * Checks if the provided string is valid
     * @param stringToCheck the string to check
     * @throws InvalidAttributeValueException if the provided string contains invalid characters
     */
    private void checkValidity(String stringToCheck) throws InvalidAttributeValueException {
        boolean isValid = JobTrackerServiceUtility.isValidString(stringToCheck);

        if (!isValid) {
            throw new InvalidAttributeValueException(String.format("Invalid attribute: %s", stringToCheck));
        }
    }
}

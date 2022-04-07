package jobservice.activity;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.converters.JobApplicationModelConverter;
import jobservice.dynamodb.JobApplicationDao;
import jobservice.dynamodb.models.JobApplication;
import jobservice.exceptions.JobApplicationNotFoundException;
import jobservice.models.JobApplicationModel;
import jobservice.models.requests.DeleteJobApplicationRequest;
import jobservice.models.results.DeleteJobApplicationResult;

import javax.inject.Inject;

/**
 * Implementation of the DeleteJobApplicationActivity for the JobService's DeleteJobApplication API
 *
 * This API allows the customer to delete an existing JobApplication
 */
public class DeleteJobApplicationActivity implements RequestHandler<DeleteJobApplicationRequest, DeleteJobApplicationResult> {
    private final JobApplicationDao jobApplicationDao;
    private JobApplicationModelConverter jobApplicationModelConverter;

    /**
     * Instantiates a new DeleteJobApplicationActivity Object
     * @param jobApplicationDao to access the JobApplication table
     * @param jobApplicationModelConverter to convert to JobApplication model
     */
    @Inject
    public DeleteJobApplicationActivity(JobApplicationDao jobApplicationDao, JobApplicationModelConverter jobApplicationModelConverter) {
        this.jobApplicationDao = jobApplicationDao;
        this.jobApplicationModelConverter = jobApplicationModelConverter;
    }

    /**
     * This method handles the incoming request by deleting the jobapplication from the database
     * with the provided username and application ID from the request.
     * It then returns the deleted question.
     * @param deleteJobApplicationRequest request object containing username and applicationId
     * @param context lambda function context
     * @return deleteJobApplicationResult result object containing the API defined {@link JobApplicationModel}
     * @throws JobApplicationNotFoundException if the requested JobApplication did not previously exist
     */
    @Override
    public DeleteJobApplicationResult handleRequest(DeleteJobApplicationRequest deleteJobApplicationRequest, Context context) throws JobApplicationNotFoundException {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log("Received Delete JobApplication request for applicationId: " + deleteJobApplicationRequest.getApplicationId());

        JobApplication jobApplication;

        try{
            jobApplication = jobApplicationDao.deleteJobApplication(deleteJobApplicationRequest.getUsername(), deleteJobApplicationRequest.getApplicationId());
        } catch (JobApplicationNotFoundException jobApplicationNotFoundException) {
            throw jobApplicationNotFoundException;
        }

        JobApplicationModel jobApplicationModel = jobApplicationModelConverter.toJobApplicationModel(jobApplication);

        return DeleteJobApplicationResult.builder()
                .withJobApplication(jobApplicationModel)
                .build();

    }
}

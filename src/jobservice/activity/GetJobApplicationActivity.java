package jobservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.converters.JobApplicationModelConverter;
import jobservice.dynamodb.JobApplicationDao;
import jobservice.dynamodb.models.JobApplication;
import jobservice.exceptions.JobApplicationNotFoundException;
import jobservice.models.JobApplicationModel;
import jobservice.models.requests.GetJobApplicationRequest;
import jobservice.models.results.GetJobApplicationResult;

import javax.inject.Inject;

/**
 * Implementation of the GetJobApplicationActivity for the JobService's GetJobApplication API
 *
 * This API allows the customer to retrieve one of their saved JobApplications
 */
public class GetJobApplicationActivity implements RequestHandler<GetJobApplicationRequest, GetJobApplicationResult> {
    public final JobApplicationDao jobApplicationDao;
    public final JobApplicationModelConverter jobApplicationModelConverter;

    /**
     * Instantiates a new GetJobApplicationActivity Object
     * @param jobApplicationDao to access the JobApplication table
     * @param jobApplicationModelConverter to convert JobApplication to JobApplication model
     */
    @Inject
    public GetJobApplicationActivity(JobApplicationDao jobApplicationDao, JobApplicationModelConverter jobApplicationModelConverter) {
        this.jobApplicationDao = jobApplicationDao;
        this.jobApplicationModelConverter = jobApplicationModelConverter;
    }

    /**
     * This method handles the incoming request by retrieving the existing
     * JobApplication with the provided username and applicationID
     *
     * It then returns the existing JobApplication details
     *
     * @param getJobApplicationRequest request object containing username and applicationId
     * @param context lambda function context
     * @return getJobApplicationResult result object containing the API defined {@link JobApplicationModel}
     * @throws JobApplicationNotFoundException if the JobApplication does not exist in the database
     */
    @Override
    public GetJobApplicationResult handleRequest(GetJobApplicationRequest getJobApplicationRequest, Context context) throws JobApplicationNotFoundException {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log("Received Get JobApplication request for applicationId: " +getJobApplicationRequest.getApplicationId());

        JobApplication jobApplication;

        try {
            jobApplication = jobApplicationDao.getJobApplication(getJobApplicationRequest.getUsername(), getJobApplicationRequest.getApplicationId());
        } catch (JobApplicationNotFoundException exception) {
            throw exception;
        }

        JobApplicationModel jobApplicationModel = jobApplicationModelConverter.toJobApplicationModel(jobApplication);

        return GetJobApplicationResult.builder()
                .withJobApplication(jobApplicationModel)
                .build();
    }

}

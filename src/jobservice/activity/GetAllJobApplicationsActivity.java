package jobservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.converters.JobApplicationModelConverter;
import jobservice.dynamodb.JobApplicationDao;
import jobservice.dynamodb.models.JobApplication;
import jobservice.exceptions.EmptyJobApplicationListException;
import jobservice.exceptions.JobApplicationNotFoundException;
import jobservice.models.JobApplicationModel;
import jobservice.models.requests.GetAllJobApplicationsRequest;
import jobservice.models.results.GetAllJobApplicationsResult;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of the GetAllJobApplicationsActivity for the JobService's GetAllJobApplications API
 *
 * This API allows the customer to retrieve all of their saved JobApplications
 */
public class GetAllJobApplicationsActivity implements RequestHandler<GetAllJobApplicationsRequest, GetAllJobApplicationsResult> {
    public final JobApplicationDao jobApplicationDao;
    public final JobApplicationModelConverter jobApplicationModelConverter;

    /**
     * Instantiates a new GetAllJobApplicationsActivity Object
     * @param jobApplicationDao to access the JobApplication table
     * @param jobApplicationModelConverter to convert JobApplication to JobApplication Model
     */
    @Inject
    public GetAllJobApplicationsActivity(JobApplicationDao jobApplicationDao, JobApplicationModelConverter jobApplicationModelConverter) {
        this.jobApplicationDao = jobApplicationDao;
        this.jobApplicationModelConverter = jobApplicationModelConverter;
    }

    /**
     * This method handles the incoming request by retrieving all of the saved JobApplications
     * associated with the given user
     * It then returns a list of all the JobApplications
     * @param getAllJobApplicationsRequest request object containing the username
     * @param context lambda function context
     * @return getAllJobApplicationsResult result object containing a list of the API defined {@link JobApplicationModel}
     */
    @Override
    public GetAllJobApplicationsResult handleRequest(GetAllJobApplicationsRequest getAllJobApplicationsRequest, Context context) {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log("Received Get All JobApplications request for user:" + getAllJobApplicationsRequest.getUsername());

        List<JobApplication> jobApplications;

        jobApplications = jobApplicationDao.getAllJobApplications(getAllJobApplicationsRequest.getUsername());


        List<JobApplicationModel> jobApplicationModels = new ArrayList<>();

        if(jobApplications != null) {
            for(JobApplication jobApp: jobApplications){
                jobApplicationModels.add(jobApplicationModelConverter.toJobApplicationModel(jobApp));
            }
        }

        return GetAllJobApplicationsResult.builder()
                .withJobApplicationList(jobApplicationModels)
                .build();
    }

}

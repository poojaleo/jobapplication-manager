package jobservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import jobservice.dynamodb.models.JobApplication;
import jobservice.exceptions.JobApplicationNotFoundException;


import javax.inject.Inject;
import java.util.List;


public class JobApplicationDao {
    private final DynamoDBMapper dynamoDBMapper;

    /**
     * Instantiates a JobApplicationDao object
     * @param dynamoDBMapper the {@link DynamoDBMapper} used to interact with the JobApplication table
     */
    @Inject
    public JobApplicationDao(DynamoDBMapper dynamoDBMapper) {this.dynamoDBMapper = dynamoDBMapper;}

    /**
     * Retrieves the JobApplication specified by the passed in username and applicationId
     * @param username - HASH Key for JobApplication Table
     * @param applicationId - RANGE Key for JobApplication Table
     * @return the JobApplication stored in the JobApplication table
     * @throws JobApplicationNotFoundException if the JobApplication does not exist in the database
     */
    public JobApplication getJobApplication(String username, String applicationId) throws JobApplicationNotFoundException {
        JobApplication jobApplication = dynamoDBMapper.load(JobApplication.class, username, applicationId);

        if(jobApplication == null) {
            throw new JobApplicationNotFoundException(String.format("Job Application %s not found.", applicationId));
        }

        return jobApplication;
    }

    /**
     * Retrieves all saved JobApplications associated with the provided username
     * Result will be an empty list if no JobApplications for the username are found
     * @param username The username to retrieve JobApplications for
     * @return the list of JobApplications associated with the username
     */
    public List<JobApplication> getAllJobApplications(String username) {
        JobApplication jobApplication = new JobApplication();
        jobApplication.setUsername(username);

        DynamoDBQueryExpression<JobApplication> queryExpression = new DynamoDBQueryExpression<JobApplication>()
                .withHashKeyValues(jobApplication)
                .withConsistentRead(false)
                .withIndexName(JobApplication.STATUS_INDEX);
        List<JobApplication> jobApplications = dynamoDBMapper.query(JobApplication.class, queryExpression);

        return jobApplications;
    }

    /**
     * Save the provided JobApplication in the DynamoDB table
     * @param jobApplication the JobApplication to be saved
     * @return the JobApplication that has been saved in our database
     */
    public JobApplication saveJobApplication(JobApplication jobApplication) {
        dynamoDBMapper.save(jobApplication);
        return jobApplication;
    }

    /**
     * Checks database to see if the generated applicationId is already in use
     * for the provided username

     * @param username the username provide
     * @param applicationId the applicationId to be checked
     * @return true if the applicationId can be used, return false if the applicationId is already in use.
     */
    public boolean idAvailable(String username, String applicationId) {
        JobApplication jobApplication = dynamoDBMapper.load(JobApplication.class, username, applicationId);

        return jobApplication == null;
    }

    /**
     * Deletes the JobApplication associated with the provided username and applicationId
     *
     * @param username the provided username
     * @param applicationId the provided applicationId
     * @return the deleted JobApplication
     * @throws JobApplicationNotFoundException if the JobApplication did not previously exist
     */
    public JobApplication deleteJobApplication(String username, String applicationId) throws JobApplicationNotFoundException  {

        JobApplication jobApplication;

        try {
            jobApplication = getJobApplication(username, applicationId);
        } catch (JobApplicationNotFoundException exception) {
            throw exception;
        }

        dynamoDBMapper.delete(jobApplication);

        return jobApplication;
    }

}

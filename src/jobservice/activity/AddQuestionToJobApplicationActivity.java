package jobservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.converters.JobApplicationModelConverter;
import jobservice.dynamodb.JobApplicationDao;
import jobservice.dynamodb.QuestionDao;
import jobservice.dynamodb.models.JobApplication;
import jobservice.exceptions.JobApplicationNotFoundException;
import jobservice.exceptions.QuestionNotFoundException;
import jobservice.models.JobApplicationModel;
import jobservice.models.requests.AddQuestionToJobApplicationRequest;
import jobservice.models.results.AddQuestionToJobApplicationResult;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the AddQuestionToJobApplication for the JobService's AddQuestionToJobApplication feature
 *
 * Allows the customer to add one of their saved questions to one of their saved JobApplications
 */
public class AddQuestionToJobApplicationActivity implements RequestHandler<AddQuestionToJobApplicationRequest, AddQuestionToJobApplicationResult> {
    private final JobApplicationDao jobApplicationDao;
    private final QuestionDao questionDao;
    private JobApplicationModelConverter jobApplicationModelConverter;

    /**
     * Instantiates a new AddQuestionToJobApplicationActivity object
     * @param jobApplicationDao to access the jobapplication table
     * @param questionDao to access the question table
     * @param jobApplicationModelConverter to convert JobApplication to JobApplication model
     */
    @Inject
    public AddQuestionToJobApplicationActivity(JobApplicationDao jobApplicationDao, QuestionDao questionDao, JobApplicationModelConverter jobApplicationModelConverter) {
        this.jobApplicationDao = jobApplicationDao;
        this.questionDao = questionDao;
        this.jobApplicationModelConverter = jobApplicationModelConverter;
    }

    /**
     * This method handles the incoming request by adding the question associated
     * with the provided questionId to the JobApplication associated with the provided
     * JobApplication for the provided username.
     *
     * It then returns the JobApplication with the updated QuestionList.
     *
     * @param addQuestionToJobApplicationRequest request object containing username, applicationId,
     *                                           and questionId
     * @param context lambda function context
     * @return addQuestionToJobApplicationResult result object containing the API defined {@link JobApplicationModel}
     * @throws JobApplicationNotFoundException if the provided JobApplication does not exist for the username
     * @throws QuestionNotFoundException if the provided Question does not exist for the username
     */
    public AddQuestionToJobApplicationResult handleRequest(AddQuestionToJobApplicationRequest addQuestionToJobApplicationRequest, Context context) throws JobApplicationNotFoundException, QuestionNotFoundException {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log("Received request to add question to application");

        JobApplication jobApplication;

        try {
            jobApplication = jobApplicationDao.getJobApplication(addQuestionToJobApplicationRequest.getUsername(), addQuestionToJobApplicationRequest.getApplicationId());
        } catch (JobApplicationNotFoundException e) {
            throw e;
        }

        try {
            questionDao.getQuestion(addQuestionToJobApplicationRequest.getUsername(), addQuestionToJobApplicationRequest.getQuestionId());
        } catch(QuestionNotFoundException exception) {
            throw exception;
        }

        List<String> questionList = new ArrayList<>();

        if(jobApplication.getQuestionsList() != null) {
            questionList = jobApplication.getQuestionsList();
        }

        if(!questionList.contains(addQuestionToJobApplicationRequest.getQuestionId())) {
            questionList.add(addQuestionToJobApplicationRequest.getQuestionId());
        }

        jobApplication.setQuestionsList(questionList);

        JobApplication jobApplicationSaved = jobApplicationDao.saveJobApplication(jobApplication);
        JobApplicationModel jobApplicationModel = jobApplicationModelConverter.toJobApplicationModel(jobApplicationSaved);

        return AddQuestionToJobApplicationResult.builder()
                .withJobApplicationModel(jobApplicationModel)
                .build();

    }


}

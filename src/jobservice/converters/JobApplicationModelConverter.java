package jobservice.converters;

import jobservice.dynamodb.models.JobApplication;
import jobservice.models.JobApplicationModel;

import javax.inject.Inject;

public class JobApplicationModelConverter {

    @Inject
    public JobApplicationModelConverter() {
    }

    /**
     * Converts the provided {@link JobApplication} to a {@link JobApplicationModel} representation
     * @param jobApplication the JobApplication to convert
     * @return jobApplicationModel the converted JobApplication Model
     */
    public JobApplicationModel toJobApplicationModel(JobApplication jobApplication) {
        JobApplicationModel jobApplicationModel = JobApplicationModel.builder()
                .withUsername(jobApplication.getUsername())
                .withApplicationId(jobApplication.getApplicationId())
                .withJobTitle(jobApplication.getJobTitle())
                .withCompany(jobApplication.getCompany())
                .withStatus(jobApplication.getStatus())
                .build();

        if(jobApplication.getLocation() != null) {
            jobApplicationModel.setLocation(jobApplication.getLocation());
        }

        if(jobApplication.getNextReminder() != null) {
            jobApplicationModel.setNextReminder(jobApplication.getNextReminder());
        }

        if(jobApplication.getNotes() != null) {
            jobApplicationModel.setNotes(jobApplication.getNotes());
        }

        if(jobApplication.getJobUrlLink() != null) {
            jobApplicationModel.setJobUrlLink(jobApplication.getJobUrlLink());
        }

        if (jobApplication.getQuestionsList() != null) {
            jobApplicationModel.setQuestionsList(jobApplication.getQuestionsList());
        }

        return jobApplicationModel;
    }

}

package jobservice.models.results;

import jobservice.models.JobApplicationModel;

public class CreateJobApplicationResult {
    private JobApplicationModel jobApplicationModel;

    public CreateJobApplicationResult(Builder builder) { this.jobApplicationModel = builder.jobApplicationModel;}

    public JobApplicationModel getJobApplicationModel() { return jobApplicationModel;}

    public void setJobApplicationModel(JobApplicationModel jobApplicationModel) {
        this.jobApplicationModel = jobApplicationModel;
    }

    public static Builder builder() { return new Builder();}

    public static final class Builder {
        private JobApplicationModel jobApplicationModel;

        public Builder withJobApplication(JobApplicationModel jobApplicationModelToUse) {
            this.jobApplicationModel = jobApplicationModelToUse;
            return this;
        }

        public CreateJobApplicationResult build() { return new CreateJobApplicationResult(this);}

    }
}

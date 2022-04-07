package jobservice.models.results;

import jobservice.models.JobApplicationModel;

public class AddQuestionToJobApplicationResult {
    private JobApplicationModel jobApplicationModel;

    public AddQuestionToJobApplicationResult(Builder builder) {this.jobApplicationModel = builder.jobApplicationModel; }

    public JobApplicationModel getJobApplicationModel() { return jobApplicationModel; }

    public void setJobApplicationModel(JobApplicationModel jobApplicationModel) {
        this.jobApplicationModel = jobApplicationModel;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder{
        private JobApplicationModel jobApplicationModel;

        public Builder() {}

        public Builder withJobApplicationModel(JobApplicationModel jobApplicationModelToUse) {
            this.jobApplicationModel = jobApplicationModelToUse;
            return this;
        }

        public AddQuestionToJobApplicationResult build() { return new AddQuestionToJobApplicationResult(this);}
    }
}

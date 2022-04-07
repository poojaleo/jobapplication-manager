package jobservice.models.results;

import jobservice.models.JobApplicationModel;

public class DeleteJobApplicationResult {
    private JobApplicationModel jobApplicationModel;

    public DeleteJobApplicationResult(Builder builder) {
        this.jobApplicationModel = builder.jobApplicationModel;
    }

    public JobApplicationModel getJobApplicationModel() {
        return jobApplicationModel;
    }

    public static Builder builder() { return new Builder(); }

    public void setJobApplicationModel(JobApplicationModel jobApplicationModel) {
        this.jobApplicationModel = jobApplicationModel;
    }

    public static final class Builder {
        private JobApplicationModel jobApplicationModel;

        public Builder withJobApplication(JobApplicationModel jobApplicationModelToUse) {
            this.jobApplicationModel = jobApplicationModelToUse;
            return this;
        }

        public DeleteJobApplicationResult build() { return new DeleteJobApplicationResult(this); }

    }
}

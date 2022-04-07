package jobservice.models.results;

import jobservice.dynamodb.models.JobApplication;
import jobservice.models.JobApplicationModel;

import java.util.List;
import java.util.Objects;

public class GetAllJobApplicationsResult {
    private List<JobApplicationModel> jobApplicationList;

    public List<JobApplicationModel> getJobApplicationList() {
        return jobApplicationList;
    }

    public GetAllJobApplicationsResult(Builder builder) {
        this.jobApplicationList = builder.jobApplicationModelList;
    }

    public void setJobApplicationList(List<JobApplicationModel> jobApplicationList) {
        this.jobApplicationList = jobApplicationList;
    }

    public static Builder builder() { return new Builder();}

    public static final class Builder {
        private List<JobApplicationModel> jobApplicationModelList;

        public Builder withJobApplicationList(List<JobApplicationModel> jobListToUse) {
            this.jobApplicationModelList = jobListToUse;
            return this;
        }

        public GetAllJobApplicationsResult build() { return new GetAllJobApplicationsResult(this);}
    }


}

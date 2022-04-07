package jobservice.models.requests;

import jobservice.models.Status;

import java.util.List;
import java.util.Objects;

public class UpdateJobApplicationRequest {
    private String username;
    private String applicationId;
    private String jobTitle;
    private String company;
    private String location;
    private Status status;
    private String nextReminder;
    private String notes;
    private String jobUrlLink;
    private List<String> questionsList;

    public UpdateJobApplicationRequest() {

    }

    public UpdateJobApplicationRequest(Builder builder){
        this.username = builder.username;
        this.applicationId = builder.applicationId;
        this.jobTitle = builder.jobTitle;
        this.company = builder.company;
        this.location = builder.location;
        this.status = builder.status;
        this.nextReminder = builder.nextReminder;
        this.notes = builder.notes;
        this.jobUrlLink = builder.jobUrlLink;
        this.questionsList = builder.questionsList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getNextReminder() {
        return nextReminder;
    }

    public void setNextReminder(String nextReminder) {
        this.nextReminder = nextReminder;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getJobUrlLink() {
        return jobUrlLink;
    }

    public void setJobUrlLink(String jobUrlLink) {
        this.jobUrlLink = jobUrlLink;
    }

    public List<String> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<String> questionsList) {
        this.questionsList = questionsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateJobApplicationRequest that = (UpdateJobApplicationRequest) o;
        return getUsername().equals(that.getUsername()) && getApplicationId().equals(that.getApplicationId()) && getJobTitle().equals(that.getJobTitle()) && getCompany().equals(that.getCompany()) && Objects.equals(getLocation(), that.getLocation()) && getStatus() == that.getStatus() && getNextReminder().equals(that.getNextReminder()) && Objects.equals(getNotes(), that.getNotes()) && Objects.equals(getJobUrlLink(), that.getJobUrlLink()) && Objects.equals(getQuestionsList(), that.getQuestionsList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getApplicationId(), getJobTitle(), getCompany(), getLocation(), getStatus(), getNextReminder(), getNotes(), getJobUrlLink(), getQuestionsList());
    }

    @Override
    public String toString() {
        return "UpdateJobApplicationRequest{" +
                "username='" + username + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", company='" + company + '\'' +
                ", location='" + location + '\'' +
                ", status=" + status +
                ", nextReminder='" + nextReminder + '\'' +
                ", notes='" + notes + '\'' +
                ", jobUrlLink='" + jobUrlLink + '\'' +
                ", questionsList=" + questionsList +
                '}';
    }

    public static Builder builder() { return new Builder();}

    public static final class Builder {
        private String username;
        private String applicationId;
        private String jobTitle;
        private String company;
        private String location;
        private Status status;
        private String nextReminder;
        private String notes;
        private String jobUrlLink;
        private List<String> questionsList;

        public Builder withUsername(String usernameToUse) {
            this.username = usernameToUse;
            return this;
        }

        public Builder withApplicationId(String applicationIdToUse) {
            this.applicationId = applicationIdToUse;
            return this;
        }

        public Builder withJobTitle(String jobTitleToUse) {
            this.jobTitle = jobTitleToUse;
            return this;
        }

        public Builder withCompany(String companyToUse) {
            this.company = companyToUse;
            return this;
        }

        public Builder withLocation(String locationToUse) {
            this.location = locationToUse;
            return this;
        }

        public Builder withStatus(Status statusToUse) {
            this.status = statusToUse;
            return this;
        }

        public Builder withNextReminder(String nextReminderToUse) {
            this.nextReminder = nextReminderToUse;
            return this;
        }

        public Builder withNotes(String notesToUse) {
            this.notes = notesToUse;
            return this;
        }

        public Builder withJobUrlLink(String jobUrlLinkToUse) {
            this.jobUrlLink = jobUrlLinkToUse;
            return this;
        }

        public Builder withQuestionList(List<String> questionListToUse) {
            this.questionsList = questionListToUse;
            return this;
        }

        public UpdateJobApplicationRequest build() { return new UpdateJobApplicationRequest(this); }
    }
}

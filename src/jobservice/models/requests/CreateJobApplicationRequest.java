package jobservice.models.requests;

import jobservice.models.JobApplicationModel;
import jobservice.models.Status;

import java.util.List;
import java.util.Objects;

public class CreateJobApplicationRequest {
    private String username;
    private String jobTitle;
    private String company;
    private String location;
    private Status status;
    private String nextReminder;
    private String notes;
    private String jobUrlLink;

    public CreateJobApplicationRequest(){}

    public CreateJobApplicationRequest(String username, String jobTitle, String company, Status status, String nextReminder){
        this.username = username;
        this.jobTitle = jobTitle;
        this.company = company;
        this.status =  status;
        this.nextReminder = nextReminder;
    }

    public CreateJobApplicationRequest(Builder builder) {
        this.username = builder.username;
        this.jobTitle = builder.jobTitle;
        this.company = builder.company;
        this.location = builder.location;
        this.status = builder.status;
        this.nextReminder = builder.nextReminder;
        this.notes = builder.notes;
        this.jobUrlLink = builder.jobUrlLink;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateJobApplicationRequest that = (CreateJobApplicationRequest) o;
        return getUsername().equals(that.getUsername()) && getJobTitle().equals(that.getJobTitle()) && getCompany().equals(that.getCompany()) && Objects.equals(getLocation(), that.getLocation()) && getStatus() == that.getStatus() && Objects.equals(getNextReminder(), that.getNextReminder()) && Objects.equals(getNotes(), that.getNotes()) && Objects.equals(getJobUrlLink(), that.getJobUrlLink());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getJobTitle(), getCompany(), getLocation(), getStatus(), getNextReminder(), getNotes(), getJobUrlLink());
    }

    @Override
    public String toString() {
        return "CreateJobApplicationRequest{" +
                "username='" + username + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", company='" + company + '\'' +
                ", location='" + location + '\'' +
                ", status=" + status +
                ", nextReminder='" + nextReminder + '\'' +
                ", notes='" + notes + '\'' +
                ", jobUrlLink='" + jobUrlLink + '\'' +
                '}';
    }



    public static Builder builder() {return new Builder();}

    public static final class Builder {
        private String username;
        private String jobTitle;
        private String company;
        private String location;
        private Status status;
        private String nextReminder;
        private String notes;
        private String jobUrlLink;

        public Builder() {
        }


        public Builder withUsername(String usernameToUse) {
            this.username = usernameToUse;
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

        public CreateJobApplicationRequest build() {return new CreateJobApplicationRequest(this);}
    }
}

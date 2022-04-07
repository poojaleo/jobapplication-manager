package jobservice.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import jobservice.models.Status;
import jobservice.models.requests.UpdateJobApplicationRequest;

import java.util.List;
import java.util.Objects;

/**
 * Represents an item in the JobApplications Table
 */
@DynamoDBTable(tableName = "JobApplications")
public class JobApplication {
    public static final String STATUS_INDEX = "StatusIndex";
    public static final String NEXT_REMINDER_INDEX = "NextReminderIndex";
    public static final String COMPANY_INDEX = "CompanyIndex";
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

    public JobApplication(){
    }

    public JobApplication(Builder builder) {
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

    @DynamoDBHashKey(attributeName = "username")
    @DynamoDBIndexHashKey(globalSecondaryIndexNames = {STATUS_INDEX, NEXT_REMINDER_INDEX, COMPANY_INDEX}, attributeName = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @DynamoDBRangeKey(attributeName = "applicationId")
    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    @DynamoDBAttribute(attributeName = "jobTitle")
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @DynamoDBAttribute(attributeName = "company")
    @DynamoDBIndexRangeKey(attributeName = "company", globalSecondaryIndexName = COMPANY_INDEX)
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @DynamoDBAttribute(attributeName = "location")
    public String getLocation() {
        return location;
    }


    public void setLocation(String location) {
        this.location = location;
    }

    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "status")
    @DynamoDBIndexRangeKey(attributeName = "status", globalSecondaryIndexName = STATUS_INDEX)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @DynamoDBAttribute(attributeName = "nextReminder")
    @DynamoDBIndexRangeKey(attributeName = "nextReminder", globalSecondaryIndexName = NEXT_REMINDER_INDEX)
    public String getNextReminder() {
        return nextReminder;
    }

    public void setNextReminder(String nextReminder) {
        this.nextReminder = nextReminder;
    }

    @DynamoDBAttribute(attributeName = "notes")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @DynamoDBAttribute(attributeName = "jobUrlLink")
    public String getJobUrlLink() {
        return jobUrlLink;
    }

    public void setJobUrlLink(String jobUrlLink) {
        this.jobUrlLink = jobUrlLink;
    }

    @DynamoDBAttribute(attributeName = "questionList")
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
        JobApplication that = (JobApplication) o;
        return getUsername().equals(that.getUsername()) && getApplicationId().equals(that.getApplicationId()) && Objects.equals(getJobTitle(), that.getJobTitle()) && Objects.equals(getCompany(), that.getCompany()) && Objects.equals(getLocation(), that.getLocation()) && Objects.equals(getStatus(), that.getStatus()) && Objects.equals(getNextReminder(), that.getNextReminder()) && Objects.equals(getNotes(), that.getNotes()) && Objects.equals(getJobUrlLink(), that.getJobUrlLink()) && Objects.equals(getQuestionsList(), that.getQuestionsList());
    }

    @Override
    public String toString() {
        return "JobApplication{" +
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

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getApplicationId());
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

        public JobApplication build() {return new JobApplication(this);}
    }
}

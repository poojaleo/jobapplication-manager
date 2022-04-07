package jobservice.models.requests;

import java.util.Objects;

public class AddQuestionToJobApplicationRequest {
    private String username;
    private String applicationId;
    private String questionId;

    public AddQuestionToJobApplicationRequest() {}

    public AddQuestionToJobApplicationRequest(String username, String questionId, String applicationId) {
        this.username = username;
        this.questionId = questionId;
        this.applicationId = applicationId;
    }

    public AddQuestionToJobApplicationRequest(Builder builder) {
        this.username = builder.username;
        this.applicationId = builder.applicationId;
        this.questionId = builder.questionId;
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

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddQuestionToJobApplicationRequest that = (AddQuestionToJobApplicationRequest) o;
        return getUsername().equals(that.getUsername()) && getApplicationId().equals(that.getApplicationId()) && getQuestionId().equals(that.getQuestionId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getApplicationId(), getQuestionId());
    }

    @Override
    public String toString() {
        return "AddQuestionToJobApplicationRequest{" +
                "username='" + username + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", questionId='" + questionId + '\'' +
                '}';
    }

    public static Builder builder() {return new Builder();}

    public static final class Builder {
        private String username;
        private String applicationId;
        private String questionId;

        private Builder() {
        }

        public Builder withUsername(String usernameToUse) {
            this.username = usernameToUse;
            return this;
        }

        public Builder withApplicationId(String applicationIdToUse) {
            this.applicationId = applicationIdToUse;
            return this;
        }

        public Builder withQuestionId(String questionIdToUse) {
            this.questionId = questionIdToUse;
            return this;
        }

        public AddQuestionToJobApplicationRequest build() {return new AddQuestionToJobApplicationRequest(this);}
    }

}

package jobservice.models.requests;

import java.util.Objects;

public class GetQuestionRequest {
    private String username;
    private String questionId;

    public GetQuestionRequest() {}

    public GetQuestionRequest(String username, String questionId) {
        this.username = username;
        this.questionId = questionId;
    }

    public GetQuestionRequest(Builder builder) {
        this.username = builder.username;
        this.questionId = builder.questionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        GetQuestionRequest that = (GetQuestionRequest) o;
        return Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getQuestionId(), that.getQuestionId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getQuestionId());
    }

    @Override
    public String toString() {
        return "GetQuestionRequest{" +
                "username='" + username + '\'' +
                ", questionId='" + questionId + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String username;
        private String questionId;

        public Builder withUsername(String usernameToUse) {
            this.username = usernameToUse;
            return this;
        }

        public Builder withQuestionId(String questionIdToUse) {
            this.questionId = questionIdToUse;
            return this;
        }

        public GetQuestionRequest build() {
            return new GetQuestionRequest(this);
        }
    }
}

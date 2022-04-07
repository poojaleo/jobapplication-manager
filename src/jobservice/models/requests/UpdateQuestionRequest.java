package jobservice.models.requests;

import java.util.List;
import java.util.Objects;

public class UpdateQuestionRequest {
    private String username;
    private String questionId;
    private String question;
    private String answer;
    private Boolean needsWork;
    private List<String> tags;

    public UpdateQuestionRequest() {}

    public UpdateQuestionRequest(String username, String questionId, String question, String answer, Boolean needsWork, List<String> tags) {
        this.username = username;
        this.questionId = questionId;
        this.question = question;
        this.answer = answer;
        this.needsWork = needsWork;
        this.tags = tags;
    }

    public UpdateQuestionRequest(Builder builder) {
        this.username = builder.username;
        this.questionId = builder.questionId;
        this.question = builder.question;
        this.answer = builder.answer;
        this.needsWork = builder.needsWork;
        this.tags = builder.tags;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getNeedsWork() {
        return needsWork;
    }

    public void setNeedsWork(Boolean needsWork) {
        this.needsWork = needsWork;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateQuestionRequest that = (UpdateQuestionRequest) o;
        return Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getQuestionId(), that.getQuestionId()) && Objects.equals(getQuestion(), that.getQuestion()) && Objects.equals(getAnswer(), that.getAnswer()) && Objects.equals(getNeedsWork(), that.getNeedsWork()) && Objects.equals(getTags(), that.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getQuestionId(), getQuestion(), getAnswer(), getNeedsWork(), getTags());
    }

    @Override
    public String toString() {
        return "UpdateQuestionRequest{" +
                "username='" + username + '\'' +
                ", questionId='" + questionId + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", needsWork=" + needsWork +
                ", tags=" + tags +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String username;
        private String questionId;
        private String question;
        private String answer;
        private Boolean needsWork;
        private List<String> tags;

        public Builder withUsername(String usernameToUse) {
            this.username = usernameToUse;
            return this;
        }

        public Builder withQuestionId(String questionIdToUse) {
            this.questionId = questionIdToUse;
            return this;
        }

        public Builder withQuestion(String questionToUse) {
            this.question = questionToUse;
            return this;
        }

        public Builder withAnswer(String answerToUse) {
            this.answer = answerToUse;
            return this;
        }

        public Builder withNeedsWork(Boolean needsWorkToUse) {
            this.needsWork = needsWorkToUse;
            return this;
        }

        public Builder withTags(List<String> tagsToUse) {
            this.tags = tagsToUse;
            return this;
        }

        public UpdateQuestionRequest build() {
            return new UpdateQuestionRequest(this);
        }
    }
}

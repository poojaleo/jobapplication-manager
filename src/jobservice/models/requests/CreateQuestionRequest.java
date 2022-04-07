package jobservice.models.requests;

import java.util.List;
import java.util.Objects;

public class CreateQuestionRequest {
    private String username;
    private String question;
    private Boolean needsWork;
    private String answer;
    private List<String> tags;

    public CreateQuestionRequest() {}

    public CreateQuestionRequest(String username, String question, Boolean needsWork) {
        this.username = username;
        this.question = question;
        this.needsWork = needsWork;
    }

    public CreateQuestionRequest(String username, String question, Boolean needsWork, String answer, List<String> tags) {
        this.username = username;
        this.question = question;
        this.needsWork = needsWork;
        this.answer = answer;
        this.tags = tags;
    }

    public CreateQuestionRequest(Builder builder) {
        this.username = builder.username;
        this.question = builder.question;
        this.needsWork = builder.needsWork;
        this.answer = builder.answer;
        this.tags = builder.tags;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Boolean getNeedsWork() {
        return needsWork;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setNeedsWork(Boolean needsWork) {
        this.needsWork = needsWork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateQuestionRequest that = (CreateQuestionRequest) o;
        return Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getQuestion(), that.getQuestion()) && Objects.equals(getNeedsWork(), that.getNeedsWork()) && Objects.equals(getAnswer(), that.getAnswer()) && Objects.equals(getTags(), that.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getQuestion(), getNeedsWork(), getAnswer(), getTags());
    }

    @Override
    public String toString() {
        return "CreateQuestionRequest{" +
                "username='" + username + '\'' +
                ", question='" + question + '\'' +
                ", needsWork=" + needsWork +
                ", answer='" + answer + '\'' +
                ", tags=" + tags +
                '}';
    }

    public static Builder builder() {return new Builder(); }

    public static final class Builder {
        private String username;
        private String question;
        private Boolean needsWork;
        private String answer;
        private List<String> tags;

        private Builder() {

        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withQuestion(String question) {
            this.question = question;
            return this;
        }

        public Builder withNeedsWork(Boolean needsWork) {
            this.needsWork = needsWork;
            return this;
        }

        public Builder withAnswer(String answer) {
            this.answer = answer;
            return this;
        }

        public Builder withTags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public CreateQuestionRequest build() {return new CreateQuestionRequest(this); }
    }
}

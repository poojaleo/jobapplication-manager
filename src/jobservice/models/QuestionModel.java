package jobservice.models;

import java.util.List;
import java.util.Objects;

public class QuestionModel {
    private String username;
    private String questionId;
    private String question;
    private String answer;
    private Boolean needsWork;
    private List<String> tags;

    public QuestionModel() {

    }

    public QuestionModel(Builder builder) {
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
        QuestionModel that = (QuestionModel) o;
        return Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getQuestionId(), that.getQuestionId()) && Objects.equals(getQuestion(), that.getQuestion()) && Objects.equals(getAnswer(), that.getAnswer()) && Objects.equals(getNeedsWork(), that.getNeedsWork()) && Objects.equals(getTags(), that.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getQuestionId(), getQuestion(), getAnswer(), getNeedsWork(), getTags());
    }

    @Override
    public String toString() {
        return "QuestionModel{" +
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

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withQuestionId(String questionId) {
            this.questionId = questionId;
            return this;
        }

        public Builder withQuestion(String question) {
            this.question = question;
            return this;
        }

        public Builder withAnswer(String answer) {
            this.answer = answer;
            return this;
        }

        public Builder withNeedsWork(Boolean needsWork) {
            this.needsWork = needsWork;
            return this;
        }

        public Builder withTags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public QuestionModel build() {
            return new QuestionModel(this);
        }
    }
}


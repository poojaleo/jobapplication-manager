package jobservice.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.List;
import java.util.Objects;

/**
 * Represents a record in the questions table.
 */
@DynamoDBTable(tableName = "Questions")
public class Question {
    public static final String NEEDSWORK_INDEX = "NeedsWorkIndex";
    public static final String QUESTION_INDEX = "QuestionExistIndex";

    private String username;
    private String questionId;
    private String question;
    private String answer;
    private Boolean needsWork;
    private List<String> tags;

    public Question() {}

    public Question(Builder builder) {
        this.username = builder.username;
        this.questionId = builder.questionId;
        this.question = builder.question;
        this.answer = builder.answer;
        this.needsWork = builder.needsWork;
        this.tags = builder.tags;
    }

    @DynamoDBHashKey(attributeName = "username")
    @DynamoDBIndexHashKey(attributeName = "username", globalSecondaryIndexNames = {NEEDSWORK_INDEX, QUESTION_INDEX})
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @DynamoDBRangeKey(attributeName = "questionId")
    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    @DynamoDBIndexRangeKey(attributeName = "question", globalSecondaryIndexName = QUESTION_INDEX)
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @DynamoDBAttribute(attributeName = "answer")
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
    @DynamoDBIndexRangeKey(attributeName = "needsWork", globalSecondaryIndexName = NEEDSWORK_INDEX)
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
        Question question1 = (Question) o;
        return Objects.equals(getUsername(), question1.getUsername())
                && Objects.equals(getQuestionId(), question1.getQuestionId())
                && Objects.equals(getQuestion(), question1.getQuestion())
                && Objects.equals(getAnswer(), question1.getAnswer())
                && Objects.equals(getNeedsWork(), question1.getNeedsWork())
                && Objects.equals(getTags(), question1.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getQuestionId(), getQuestion(), getAnswer(), getNeedsWork(), getTags());
    }

    public static Builder builder() {return new Builder();}

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

        public Question build() {
            return new Question(this);
        }
    }
}


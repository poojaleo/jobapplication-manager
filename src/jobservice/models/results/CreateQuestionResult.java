package jobservice.models.results;

import jobservice.models.QuestionModel;

public class CreateQuestionResult {
    private QuestionModel question;

    public CreateQuestionResult(Builder builder) {
        this.question = builder.question;
    }

    public QuestionModel getQuestion() {
        return question;
    }

    public void setQuestion(QuestionModel question) {
        this.question = question;
    }

    public static Builder builder() {return new Builder();}

    public static final class Builder {
        private QuestionModel question;

        public Builder withQuestion(QuestionModel question) {
            this.question = question;
            return this;
        }

        public CreateQuestionResult build() {
            return new CreateQuestionResult(this);
        }
    }
}

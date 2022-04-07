package jobservice.models.results;

import jobservice.models.QuestionModel;

public class UpdateQuestionResult {
    private QuestionModel questionModel;

    public UpdateQuestionResult(Builder builder) {
        this.questionModel = builder.questionModel;
    }

    public QuestionModel getQuestion() {
        return questionModel;
    }

    public void setQuestionModel(QuestionModel questionModel) {
        this.questionModel = questionModel;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private QuestionModel questionModel;

        public Builder withQuestion(QuestionModel questionModelToUse) {
            this.questionModel = questionModelToUse;
            return this;
        }

        public UpdateQuestionResult build() {
            return new UpdateQuestionResult(this);
        }
    }
}

package jobservice.models.results;

import jobservice.models.QuestionModel;

public class GetQuestionResult {
    private QuestionModel questionModel;

    public GetQuestionResult(Builder builder) {
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

        public GetQuestionResult build() {
            return new GetQuestionResult(this);
        }
    }
}

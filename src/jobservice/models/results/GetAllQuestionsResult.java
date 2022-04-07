package jobservice.models.results;

import jobservice.models.QuestionModel;

import java.util.List;

public class GetAllQuestionsResult {
    List<QuestionModel> questions;

    public GetAllQuestionsResult (Builder builder) {
        this.questions = builder.questions;
    }

    public List<QuestionModel> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionModel> questions) {
        this.questions = questions;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        List<QuestionModel> questions;

        public Builder withQuestions(List<QuestionModel> questionsToUse) {
            this.questions = questionsToUse;
            return this;
        }

        public GetAllQuestionsResult build() {
            return new GetAllQuestionsResult(this);
        }
    }
}

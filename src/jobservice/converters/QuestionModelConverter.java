package jobservice.converters;

import jobservice.dynamodb.models.Question;
import jobservice.models.QuestionModel;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class QuestionModelConverter {

    @Inject
    public QuestionModelConverter() {
    }

    /**
     * Converts a provided {@link Question} into a {@link QuestionModel} representation.
     * @param question the question to convert
     * @return the converted questionModel
     */
    public QuestionModel toQuestionModel(Question question) {
        QuestionModel questionModel = QuestionModel.builder()
                .withUsername(question.getUsername())
                .withQuestionId(question.getQuestionId())
                .withQuestion(question.getQuestion())
                .withNeedsWork(question.getNeedsWork())
                .build();

        if (question.getAnswer() != null) {
            questionModel.setAnswer(question.getAnswer());
        }
        if (question.getTags() != null) {
            questionModel.setTags(question.getTags());
        }

        return questionModel;
    }


    /**
     * Converts a provided {@link List<Question>} into a {@link List<QuestionModel>} representation.
     * @param questions the list of questions to convert
     * @return the converted list of questionModels
     */
    public List<QuestionModel> toQuestionModelList(List<Question> questions) {
        List<QuestionModel> questionModels = new ArrayList<>();

        for (Question question:
             questions) {
            QuestionModel questionModel = QuestionModel.builder()
                    .withUsername(question.getUsername())
                    .withQuestionId(question.getQuestionId())
                    .withQuestion(question.getQuestion())
                    .withNeedsWork(question.getNeedsWork())
                    .build();

            if (question.getAnswer() != null) {
                questionModel.setAnswer(question.getAnswer());
            }
            if (question.getTags() != null) {
                questionModel.setTags(question.getTags());
            }

            questionModels.add(questionModel);
        }
        return questionModels;
    }
}

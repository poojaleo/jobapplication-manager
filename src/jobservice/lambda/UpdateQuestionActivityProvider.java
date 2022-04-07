package jobservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.dependency.DaggerServiceComponent;
import jobservice.dependency.ServiceComponent;
import jobservice.models.requests.UpdateQuestionRequest;
import jobservice.models.results.UpdateQuestionResult;

public class UpdateQuestionActivityProvider implements RequestHandler<UpdateQuestionRequest, UpdateQuestionResult> {
    public UpdateQuestionActivityProvider() {
    }

    public UpdateQuestionResult handleRequest(final UpdateQuestionRequest updateQuestionRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideUpdateQuestionActivity().handleRequest(updateQuestionRequest, context);
    }
}

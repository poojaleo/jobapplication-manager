package jobservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.dependency.DaggerServiceComponent;
import jobservice.dependency.ServiceComponent;
import jobservice.models.requests.DeleteQuestionRequest;
import jobservice.models.results.DeleteQuestionResult;

public class DeleteQuestionActivityProvider implements RequestHandler<DeleteQuestionRequest, DeleteQuestionResult> {
    public DeleteQuestionActivityProvider() {
    }

    public DeleteQuestionResult handleRequest(final DeleteQuestionRequest deleteQuestionRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideDeleteQuestionActivity().handleRequest(deleteQuestionRequest, context);
    }
}

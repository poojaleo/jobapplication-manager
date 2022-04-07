package jobservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.dependency.DaggerServiceComponent;
import jobservice.dependency.ServiceComponent;
import jobservice.models.requests.CreateQuestionRequest;
import jobservice.models.results.CreateQuestionResult;

public class CreateQuestionActivityProvider implements RequestHandler<CreateQuestionRequest, CreateQuestionResult> {

    public CreateQuestionActivityProvider() {}

    @Override
    public CreateQuestionResult handleRequest(final CreateQuestionRequest createQuestionRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideCreateQuestionActivity().handleRequest(createQuestionRequest, context);
    }
}

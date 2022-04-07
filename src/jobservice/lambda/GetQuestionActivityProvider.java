package jobservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.dependency.DaggerServiceComponent;
import jobservice.dependency.ServiceComponent;
import jobservice.models.requests.GetQuestionRequest;
import jobservice.models.results.GetQuestionResult;

public class GetQuestionActivityProvider implements RequestHandler<GetQuestionRequest, GetQuestionResult> {

    public GetQuestionActivityProvider() {}

    @Override
    public GetQuestionResult handleRequest(final GetQuestionRequest getQuestionRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideGetQuestionActivity().handleRequest(getQuestionRequest, context);
    }
}

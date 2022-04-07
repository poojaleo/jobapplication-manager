package jobservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.dependency.DaggerServiceComponent;
import jobservice.dependency.ServiceComponent;
import jobservice.models.requests.GetAllQuestionsRequest;
import jobservice.models.results.GetAllQuestionsResult;

public class GetAllQuestionsActivityProvider implements RequestHandler<GetAllQuestionsRequest, GetAllQuestionsResult> {

    public GetAllQuestionsActivityProvider() {
    }

    public GetAllQuestionsResult handleRequest(final GetAllQuestionsRequest getAllQuestionsRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideGetAllQuestionsActivity().handleRequest(getAllQuestionsRequest, context);
    }
}

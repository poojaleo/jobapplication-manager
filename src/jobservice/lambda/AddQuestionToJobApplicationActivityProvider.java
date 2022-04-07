package jobservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.activity.AddQuestionToJobApplicationActivity;
import jobservice.dependency.DaggerServiceComponent;
import jobservice.dependency.ServiceComponent;
import jobservice.models.requests.AddQuestionToJobApplicationRequest;
import jobservice.models.results.AddQuestionToJobApplicationResult;

public class AddQuestionToJobApplicationActivityProvider implements RequestHandler<AddQuestionToJobApplicationRequest, AddQuestionToJobApplicationResult> {
    private AddQuestionToJobApplicationActivity addQuestionToJobApplicationActivity;

    public AddQuestionToJobApplicationActivityProvider() {}

    @Override
    public AddQuestionToJobApplicationResult handleRequest(AddQuestionToJobApplicationRequest addQuestionToJobApplicationRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideAddQuestionToJobApplicationActivity().handleRequest(addQuestionToJobApplicationRequest, context);
    }
}

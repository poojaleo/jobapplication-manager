package jobservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.activity.CreateJobApplicationActivity;
import jobservice.dependency.DaggerServiceComponent;
import jobservice.dependency.ServiceComponent;
import jobservice.models.requests.CreateJobApplicationRequest;
import jobservice.models.results.CreateJobApplicationResult;

public class CreateJobApplicationActivityProvider implements RequestHandler<CreateJobApplicationRequest, CreateJobApplicationResult> {

    private CreateJobApplicationActivity createJobApplicationActivity;

    public CreateJobApplicationActivityProvider() {
    }

    @Override
    public CreateJobApplicationResult handleRequest(CreateJobApplicationRequest createJobApplicationRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideCreateJobApplicationActivity().handleRequest(createJobApplicationRequest, context);
    }

}

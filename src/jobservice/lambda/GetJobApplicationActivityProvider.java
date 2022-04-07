package jobservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.dependency.DaggerServiceComponent;
import jobservice.dependency.ServiceComponent;
import jobservice.models.requests.GetJobApplicationRequest;
import jobservice.models.results.GetJobApplicationResult;

public class GetJobApplicationActivityProvider implements RequestHandler<GetJobApplicationRequest, GetJobApplicationResult> {

    public GetJobApplicationActivityProvider() {
    }

    @Override
    public GetJobApplicationResult handleRequest(GetJobApplicationRequest getJobApplicationRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideGetJobApplicationActivity().handleRequest(getJobApplicationRequest, context);
    }

}

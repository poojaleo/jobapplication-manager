package jobservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.dependency.DaggerServiceComponent;
import jobservice.dependency.ServiceComponent;
import jobservice.models.requests.UpdateJobApplicationRequest;
import jobservice.models.results.UpdateJobApplicationResult;

public class UpdateJobApplicationActivityProvider implements RequestHandler<UpdateJobApplicationRequest, UpdateJobApplicationResult> {

    public UpdateJobApplicationActivityProvider() {

    }

    @Override
    public UpdateJobApplicationResult handleRequest(UpdateJobApplicationRequest updateJobApplicationRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideUpdateJobApplicationActivity().handleRequest(updateJobApplicationRequest, context);
    }

}

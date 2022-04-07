package jobservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import dagger.Provides;
import jobservice.dependency.DaggerServiceComponent;
import jobservice.dependency.ServiceComponent;
import jobservice.models.requests.DeleteJobApplicationRequest;
import jobservice.models.results.DeleteJobApplicationResult;

public class DeleteJobApplicationActivityProvider implements RequestHandler<DeleteJobApplicationRequest, DeleteJobApplicationResult> {

    public DeleteJobApplicationActivityProvider() {
    }

    @Override
    public DeleteJobApplicationResult handleRequest(DeleteJobApplicationRequest deleteJobApplicationRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideDeleteJobApplicationActivity().handleRequest(deleteJobApplicationRequest, context);
    }
}

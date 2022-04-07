package jobservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.dependency.DaggerServiceComponent;
import jobservice.dependency.ServiceComponent;
import jobservice.models.requests.GetAllJobApplicationsRequest;
import jobservice.models.results.GetAllJobApplicationsResult;

public class GetAllJobApplicationsActivityProvider implements RequestHandler<GetAllJobApplicationsRequest, GetAllJobApplicationsResult> {

    public GetAllJobApplicationsActivityProvider() {

    }

    @Override
    public GetAllJobApplicationsResult handleRequest(GetAllJobApplicationsRequest getAllJobApplicationsRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideGetAllJobApplicationsActivity().handleRequest(getAllJobApplicationsRequest, context);
    }

}

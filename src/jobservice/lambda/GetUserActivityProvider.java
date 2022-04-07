package jobservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.dependency.DaggerServiceComponent;
import jobservice.dependency.ServiceComponent;
import jobservice.models.requests.GetUserRequest;
import jobservice.models.results.GetUserResult;

/**
 * Implementation of the GetUserActivityProvider to interact with the AWS Lambda function
 */
public class GetUserActivityProvider implements RequestHandler<GetUserRequest, GetUserResult> {

    public GetUserActivityProvider() {
    }

    /**
     *
     * @param getUserRequest {@link GetUserRequest}
     * @param context AWS lambda context
     * @return {@link GetUserResult}
     */
    @Override
    public GetUserResult handleRequest(GetUserRequest getUserRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideGetUserActivity().handleRequest(getUserRequest, context);
    }
}

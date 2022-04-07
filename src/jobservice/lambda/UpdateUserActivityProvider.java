package jobservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.dependency.DaggerServiceComponent;
import jobservice.dependency.ServiceComponent;
import jobservice.models.requests.UpdateUserRequest;
import jobservice.models.results.UpdateUserResult;

/**
 * Implementation of the UpdateUserActivityProvider to interact with the AWS Lambda function
 */
public class UpdateUserActivityProvider implements RequestHandler<UpdateUserRequest, UpdateUserResult> {

    public UpdateUserActivityProvider() {
    }

    /**
     *
     * @param updateUserRequest {@link UpdateUserRequest}
     * @param context AWS Lambda context
     * @return {@link UpdateUserResult}
     */

    @Override
    public UpdateUserResult handleRequest(UpdateUserRequest updateUserRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideUpdateUserActivity().handleRequest(updateUserRequest, context);
    }
}

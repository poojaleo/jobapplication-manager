package jobservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.dependency.DaggerServiceComponent;
import jobservice.dependency.ServiceComponent;
import jobservice.models.requests.CreateUserRequest;
import jobservice.models.results.CreateUserResult;

/**
 * Implementation of the CreateUserActivityProvider to interact with the AWS Lambda function
 */
public class CreateUserActivityProvider implements RequestHandler<CreateUserRequest, CreateUserResult> {

    public CreateUserActivityProvider() {
    }

    /**
     *
     * @param createUserRequest {@link CreateUserRequest}
     * @param context AWS lambda context
     * @return {@link CreateUserResult}
     */

    @Override
    public CreateUserResult handleRequest(CreateUserRequest createUserRequest, Context context) {
        ServiceComponent dagger = DaggerServiceComponent.create();
        return dagger.provideCreateUserActivity().handleRequest(createUserRequest,context);
    }
}

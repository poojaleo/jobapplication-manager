package jobservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.converters.UserModelConverter;
import jobservice.dynamodb.UserDao;
import jobservice.dynamodb.models.User;
import jobservice.exceptions.InvalidPasswordException;
import jobservice.exceptions.UserNotFoundException;
import jobservice.models.UserModel;
import jobservice.models.requests.GetUserRequest;
import jobservice.models.results.GetUserResult;

import javax.inject.Inject;

/**
 * Implementation of the GetUserActivity for the JobService's GetUser API.
 *
 * This API allows the customer to get an existing user.
 */
public class GetUserActivity implements RequestHandler<GetUserRequest, GetUserResult> {
    private final UserDao userDao;
    private UserModelConverter userModelConverter;

    /**
     * Instantiates a new GetUserActivity object.
     *
     * @param userDao userDao to access the users table.
     * @param userModelConverter to convert user to userModel
     */

    @Inject
    public GetUserActivity(UserDao userDao, UserModelConverter userModelConverter) {
        this.userDao = userDao;
        this.userModelConverter = userModelConverter;
    }

    /**
     * This method handles the incoming request by getting an existing user
     * with the provided user's username and password
     * <p>
     * It then returns the existing user details
     * <p>
     * @param getUserRequest request object containing the username and password associated with it
     * @param context lambda function context
     * @return GetUserResult object containing the API defined {@link UserModel}
     * @throws UserNotFoundException If the provided user does not exist
     */
    @Override
    public GetUserResult handleRequest(GetUserRequest getUserRequest, Context context) throws UserNotFoundException {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log("Received Get User Request for username : " + getUserRequest.getUsername());

        User user;

        try {
            user = userDao.getUser(getUserRequest.getUsername());
        } catch (UserNotFoundException userNotFoundException) {
            throw userNotFoundException;
        }

        boolean isValidPassword = user.getPassword().equals(getUserRequest.getPassword());

        if(!isValidPassword) {
            throw new InvalidPasswordException("Password do not match. Please enter a valid password");
        }

        UserModel userModel = userModelConverter.toUserModel(user);

        return GetUserResult.builder()
                .withUser(userModel)
                .build();
    }
}

package jobservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.converters.UserModelConverter;
import jobservice.dynamodb.UserDao;
import jobservice.dynamodb.models.User;
import jobservice.exceptions.InvalidAttributeChangeException;
import jobservice.exceptions.InvalidAttributeValueException;
import jobservice.exceptions.UserNotFoundException;
import jobservice.models.UserModel;
import jobservice.models.requests.UpdateUserRequest;
import jobservice.models.results.UpdateUserResult;
import jobservice.util.JobTrackerServiceUtility;

import javax.inject.Inject;

/**
 * Implementation of the UpdateUserActivity for the JobService's UpdateUser API.
 *
 * This API allows the customer to update an existing user.
 */
public class UpdateUserActivity implements RequestHandler<UpdateUserRequest, UpdateUserResult> {
    private final UserDao userDao;
    private UserModelConverter userModelConverter;

    /**
     * Instantiates a new UpdateUserActivity object.
     *
     * @param userDao userDao to access the users table.
     * @param userModelConverter to convert user to userModel
     */
    @Inject
    public UpdateUserActivity(UserDao userDao, UserModelConverter userModelConverter) {
        this.userDao = userDao;
        this.userModelConverter = userModelConverter;
    }

    /**
     * This method handles the incoming request by updating an existing user
     * with the provided user's username
     * @param updateUserRequest request object containing the username and variables that needs to be updated
     * @param context lambda function context
     * @return UpdateUserResult object containing the API defined {@link UserModel}
     * @throws InvalidAttributeValueException If the provided username or firstname or lastname or
     *      emailAddress provided to update has invalid characters
     * @throws UserNotFoundException If the provided user does not exist
     * @throws InvalidAttributeChangeException Email address update is not allowed. Throws the error if user requests
     * to change the email address
     */


    @Override
    public UpdateUserResult handleRequest(UpdateUserRequest updateUserRequest, Context context) throws
            InvalidAttributeValueException, InvalidAttributeChangeException, UserNotFoundException {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log("Received Update User Request for user: " + updateUserRequest.toString());

        User user;

        try {
            user = userDao.getUser(updateUserRequest.getUsername());
        } catch (UserNotFoundException userNotFoundException) {
            throw userNotFoundException;
        }

        if(updateUserRequest.getFirstname() != null && !updateUserRequest.getFirstname().equals("") && !user.getFirstname().equals(updateUserRequest.getFirstname())) {
            try {
                checkNameValidity(updateUserRequest.getFirstname());
            } catch (InvalidAttributeValueException exception) {
                throw exception;
            }
            user.setFirstname(updateUserRequest.getFirstname());
        }
        
        if(updateUserRequest.getLastname() != null && !updateUserRequest.getLastname().equals("") && !user.getLastname().equals(updateUserRequest.getLastname())) {
                try {
                    checkNameValidity(updateUserRequest.getLastname());
                } catch (InvalidAttributeValueException exception) {
                    throw exception;
                }
            user.setLastname(updateUserRequest.getLastname());
        }

        if(updateUserRequest.getEmailAddress() != null && !updateUserRequest.getEmailAddress().equals("") && !user.getEmailAddress().equals(updateUserRequest.getEmailAddress())) {
            throw new InvalidAttributeChangeException("Email Address update is not allowed.");
        }

        if(updateUserRequest.getPassword() != null && !updateUserRequest.getPassword().equals("") && !user.getPassword().equals(updateUserRequest.getPassword())) {
            try {
                checkPasswordValidity(updateUserRequest.getPassword());
            } catch (InvalidAttributeValueException exception) {
                throw exception;
            }
            user.setPassword(updateUserRequest.getPassword());
        }

        User userSaved = userDao.saveUser(user);

        UserModel userModel = userModelConverter.toUserModel(userSaved);

        return UpdateUserResult.builder()
                .withUser(userModel)
                .build();

    }

    /**
     * check if it's a valid string
     * @param stringToCheck string to validate
     * @throws InvalidAttributeValueException if invalid string
     */


    private void checkNameValidity(String stringToCheck) throws InvalidAttributeValueException {
        boolean isValidUserString = JobTrackerServiceUtility.isValidString(stringToCheck);
        if(!isValidUserString) {
            throw new InvalidAttributeValueException(String.format("Invalid Field: %s", stringToCheck));
        }
    }

    /**
     * check if it meets the password requirements i.e. minimum eight characters, at least one uppercase letter,
     * one lowercase letter, one number and one special character
     * @param passwordToCheck password to validate
     * @throws InvalidAttributeValueException if invalid password
     */

    private void checkPasswordValidity(String passwordToCheck) throws InvalidAttributeValueException {
        boolean isValidPassword = JobTrackerServiceUtility.isValidPassword(passwordToCheck);

        if(!isValidPassword) {
            throw new InvalidAttributeValueException("Invalid Password. Password should contain minimum eight characters," +
                    " at least one uppercase letter, one lowercase letter, one number and one special character");
        }
    }
}

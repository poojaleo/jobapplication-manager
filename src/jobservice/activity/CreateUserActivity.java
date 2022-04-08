package jobservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jobservice.converters.UserModelConverter;
import jobservice.dynamodb.UserDao;
import jobservice.dynamodb.models.User;
import jobservice.exceptions.InvalidAttributeValueException;
import jobservice.exceptions.UserAlreadyExistsException;
import jobservice.exceptions.UserNotFoundException;
import jobservice.models.UserModel;
import jobservice.models.requests.CreateUserRequest;
import jobservice.models.results.CreateUserResult;
import jobservice.util.JobTrackerServiceUtility;

import javax.inject.Inject;

/**
 * Implementation of the CreateUserActivity for the JobService's CreateUser API.
 *
 * This API allows the customer to create a new user.
 */
public class CreateUserActivity implements RequestHandler<CreateUserRequest, CreateUserResult> {
    private final UserDao userDao;
    private UserModelConverter userModelConverter;

    /**
     * Instantiates a new CreateUserActivity object.
     *
     * @param userDao userDao to access the users table.
     * @param userModelConverter to convert user to userModel
     */

    @Inject
    public CreateUserActivity(UserDao userDao, UserModelConverter userModelConverter) {
        this.userDao = userDao;
        this.userModelConverter = userModelConverter;
    }

    /**
     * This method handles the incoming request by creating a new user
     * with the provided user's username and user details - firstname, lastname, emailAddress, password
     * <p>
     * It then returns the newly created user
     * <p>
     *
     * @param createUserRequest request object containing the username and customer ID
     *                              associated with it
     * @return createUserResult result object containing the API defined {@link UserModel}
     * @throws InvalidAttributeValueException If the provided username or firstname or lastname or
     * emailAddress has invalid characters
     * @throws UserAlreadyExistsException  If the provided username already exists
     */

    @Override
    public CreateUserResult handleRequest(CreateUserRequest createUserRequest, Context context) throws
            InvalidAttributeValueException, UserAlreadyExistsException {
        LambdaLogger lambdaLogger = context.getLogger();
        lambdaLogger.log("Received Create User Request for username : " + createUserRequest.getUsername());

        try {
            checkNameValidity(createUserRequest.getUsername());
            checkNameValidity(createUserRequest.getFirstname());
            if(createUserRequest.getLastname() != null && !createUserRequest.getLastname().equals(""))
                checkNameValidity(createUserRequest.getLastname());
            checkEmailAddressValidity(createUserRequest.getEmailAddress());
            checkPasswordValidity(createUserRequest.getPassword());
        } catch (InvalidAttributeValueException exception) {
            throw new InvalidAttributeValueException(exception.getMessage());
        }

        lambdaLogger.log("Validation complete.");

        User optionalUser = null;
        
        try {
            optionalUser = userDao.getUser(createUserRequest.getUsername());
        } catch (UserNotFoundException exception) {
            lambdaLogger.log("Username does not exist. New user creation in progress");
        }

        if(optionalUser != null) {
            throw new UserAlreadyExistsException(String.format
                    ("User with username: %s already exists", createUserRequest.getUsername()));
        }

        User user = User.builder()
                .withUsername(createUserRequest.getUsername())
                .withFirstname(createUserRequest.getFirstname())
                .withEmailAddress(createUserRequest.getEmailAddress())
                .withPassword(createUserRequest.getPassword())
                .build();

        if(createUserRequest.getLastname() != null)
            user.setLastname(createUserRequest.getLastname());

        User userSaved = userDao.saveUser(user);
        UserModel userModel = userModelConverter.toUserModel(userSaved);

        return CreateUserResult.builder()
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
     * check is the email is a valid email address
     * @param emailToCheck email to validate
     * @throws InvalidAttributeValueException if invalid email address
     */

    private void checkEmailAddressValidity(String emailToCheck) throws InvalidAttributeValueException {
        boolean isValidEmail = JobTrackerServiceUtility.isValidEmailAddress(emailToCheck);

        if(!isValidEmail) {
            throw new InvalidAttributeValueException(String.format("Invalid Email Address: %s", emailToCheck));
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

package jobservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import jobservice.converters.UserModelConverter;
import jobservice.dynamodb.UserDao;
import jobservice.dynamodb.models.User;
import jobservice.exceptions.InvalidAttributeChangeException;
import jobservice.exceptions.InvalidAttributeValueException;
import jobservice.exceptions.UserNotFoundException;
import jobservice.models.UserModel;
import jobservice.models.requests.UpdateUserRequest;
import jobservice.models.results.UpdateUserResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UpdateUserActivityTest {
    private final String usernameMickey = "mickeymouse";
    private final String firstnameMickey = "Mickey";
    private final String updatedFirstnameMickey = "MickeyandMinnie";
    private final String lastNameMickey = "Mouse";
    private final String validEmailMickey = "mickey@yahoo.com";
    private final String validPasswordMickey = "Mickey@123";
    private final String validUpdatedPasswordMickey = "MickeyMinnie@123";
    private final String invalidEmailMickey = "mickey@gmail";
    private final String invalidName = "mick\\ey";
    private final String invalidPasswordMickey = "123456@";
    private final String usernameNotInDatabase = "donaldduck";

    @Mock
    private UserDao userDao;
    @Mock
    private UserModelConverter userModelConverter;

    @Mock
    private Context context;

    private UpdateUserActivity updateUserActivity;
    private LambdaLogger lambdaLogger;
    User user;

    @BeforeEach
    public void setup() {
        initMocks(this);
        updateUserActivity = new UpdateUserActivity(userDao, userModelConverter);
        user = User.builder()
                .withUsername(usernameMickey)
                .withFirstname(firstnameMickey)
                .withLastname(lastNameMickey)
                .withEmailAddress(validEmailMickey)
                .withPassword(validPasswordMickey)
                .build();
        lambdaLogger = new LambdaLogger() {
            @Override
            public void log(String message) {
                System.out.println(message);
            }

            @Override
            public void log(byte[] message) {
                System.out.println(message);
            }
        };
    }

    @Test
    public void handleRequest_updateUserFirstname_returnsUpdateUserResult() {
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .withUsername(usernameMickey)
                .withFirstname(updatedFirstnameMickey)
                .build();

        User userSaved = User.builder()
                .withUsername(usernameMickey)
                .withFirstname(updatedFirstnameMickey)
                .withLastname(lastNameMickey)
                .withEmailAddress(validEmailMickey)
                .withPassword(validPasswordMickey)
                .build();

        UserModel userModel = UserModel.builder()
                .withUsername(usernameMickey)
                .withFirstname(updatedFirstnameMickey)
                .withLastname(lastNameMickey)
                .withEmailAddress(validEmailMickey)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(userDao.getUser(usernameMickey)).thenReturn(user);
        when(userDao.saveUser(userSaved)).thenReturn(userSaved);
        when(userModelConverter.toUserModel(userSaved)).thenReturn(userModel);

        UpdateUserResult updateUserResult = updateUserActivity.handleRequest(updateUserRequest, context);

        assertEquals(usernameMickey, updateUserResult.getUserModel().getUsername(),
                "Expected username to be " + usernameMickey);
        assertEquals(updatedFirstnameMickey, updateUserResult.getUserModel().getFirstname(),
                "Expected firstname to be " + updatedFirstnameMickey);
        assertEquals(lastNameMickey, updateUserResult.getUserModel().getLastname(),
                "Expected lastname to be " + lastNameMickey);
        assertEquals(validEmailMickey, updateUserResult.getUserModel().getEmailAddress(),
                "Expected Email Address to be " + validEmailMickey);

    }

    @Test
    public void handleRequest_updateUserPassword_returnsUpdateUserResult() {
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .withUsername(usernameMickey)
                .withPassword(validUpdatedPasswordMickey)
                .build();

        User userSaved = User.builder()
                .withUsername(usernameMickey)
                .withFirstname(firstnameMickey)
                .withLastname(lastNameMickey)
                .withEmailAddress(validEmailMickey)
                .withPassword(validUpdatedPasswordMickey)
                .build();

        UserModel userModel = UserModel.builder()
                .withUsername(usernameMickey)
                .withFirstname(firstnameMickey)
                .withLastname(lastNameMickey)
                .withEmailAddress(validEmailMickey)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(userDao.getUser(usernameMickey)).thenReturn(user);
        when(userDao.saveUser(userSaved)).thenReturn(userSaved);
        when(userModelConverter.toUserModel(userSaved)).thenReturn(userModel);

        UpdateUserResult updateUserResult = updateUserActivity.handleRequest(updateUserRequest, context);

        assertEquals(usernameMickey, updateUserResult.getUserModel().getUsername(),
                "Expected username to be " + usernameMickey);
        assertEquals(firstnameMickey, updateUserResult.getUserModel().getFirstname(),
                "Expected firstname to be " + updatedFirstnameMickey);
        assertEquals(lastNameMickey, updateUserResult.getUserModel().getLastname(),
                "Expected lastname to be " + lastNameMickey);
        assertEquals(validEmailMickey, updateUserResult.getUserModel().getEmailAddress(),
                "Expected Email Address to be " + validEmailMickey);
    }

    @Test
    public void handleRequest_updateUserPasswordWithInvalidRequirements_throwsException() {
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .withUsername(usernameMickey)
                .withPassword(invalidPasswordMickey)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(userDao.getUser(usernameMickey)).thenReturn(user);

        assertThrows(InvalidAttributeValueException.class, () -> {
            updateUserActivity.handleRequest(updateUserRequest, context);
        });

    }

    @Test
    public void handleRequest_userNotInDatabase_throwsUserNotFoundException() {
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .withUsername(usernameNotInDatabase)
                .withFirstname("Donald")
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(userDao.getUser(usernameNotInDatabase)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> {
            updateUserActivity.handleRequest(updateUserRequest, context);
        });
    }

    @Test
    public void handleRequest_updateUserFirstnameWithInvalidString_throwsException() {
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .withUsername(usernameMickey)
                .withFirstname(invalidName)
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(userDao.getUser(usernameMickey)).thenReturn(user);

        assertThrows(InvalidAttributeValueException.class, () -> {
            updateUserActivity.handleRequest(updateUserRequest, context);
        });
    }

    @Test
    public void handleRequest_updateUserEmailAddress_throwsInvalidChangeException() {
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .withUsername(usernameMickey)
                .withEmailAddress("abc@gmail.com")
                .build();

        when(context.getLogger()).thenReturn(lambdaLogger);
        when(userDao.getUser(usernameMickey)).thenReturn(user);

        assertThrows(InvalidAttributeChangeException.class, () -> {
            updateUserActivity.handleRequest(updateUserRequest, context);
        });
    }








}

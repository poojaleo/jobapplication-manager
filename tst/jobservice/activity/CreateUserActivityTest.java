package jobservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import jobservice.converters.UserModelConverter;
import jobservice.dynamodb.UserDao;
import jobservice.dynamodb.models.User;
import jobservice.exceptions.InvalidAttributeValueException;
import jobservice.exceptions.UserAlreadyExistsException;
import jobservice.models.UserModel;
import jobservice.models.requests.CreateUserRequest;
import jobservice.models.results.CreateUserResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CreateUserActivityTest {

    private final String usernameMickey = "mickeymouse";
    private final String firstnameMickey = "Mickey";
    private final String lastNameMickey = "Mouse";
    private final String validEmailMickey = "mickey@yahoo.com";
    private final String validPasswordMickey = "Mickey@123";
    private final String invalidEmailMickey = "mickey@gmail";
    private final String invalidUsername = "mick\\ey";
    private final String invalidPasswordMickey = "123456@";

    @Mock
    private UserDao userDao;
    @Mock
    private UserModelConverter userModelConverter;

    @Mock
    private Context context;

    private CreateUserActivity createUserActivity;
    private LambdaLogger lambdaLogger;

    @BeforeEach
    public void setup() {
        initMocks(this);
        createUserActivity = new CreateUserActivity(userDao, userModelConverter);
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
    public void handleRequest_createUserWithLastname_returnsCreateUserResult() {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .withUsername(usernameMickey)
                .withFirstname(firstnameMickey)
                .withLastname(lastNameMickey)
                .withEmailAddress(validEmailMickey)
                .withPassword(validPasswordMickey)
                .build();

        when(userDao.saveUser(any(User.class))).then(AdditionalAnswers.returnsFirstArg());
        when(context.getLogger()).thenReturn(lambdaLogger);

        UserModel userModel = UserModel.builder()
                .withUsername(usernameMickey)
                .withFirstname(firstnameMickey)
                .withLastname(lastNameMickey)
                .withEmailAddress(validEmailMickey)
                .build();

        when(userModelConverter.toUserModel(any(User.class))).thenReturn(userModel);

        CreateUserResult createUserResult = createUserActivity.handleRequest(createUserRequest, context);

        assertEquals(usernameMickey, createUserResult.getUser().getUsername(),
                "Expected username to be " + usernameMickey);
        assertEquals(firstnameMickey, createUserResult.getUser().getFirstname(),
                "Expected firstname to be " + firstnameMickey);
        assertEquals(lastNameMickey, createUserResult.getUser().getLastname(),
                "Expected lastname to be " + lastNameMickey);
        assertEquals(validEmailMickey, createUserResult.getUser().getEmailAddress(),
                "Expected Email Address to be " + validEmailMickey);

    }

    @Test
    public void handleRequest_createUserWithExistingUsername_throwsUserAlreadyExistsError() {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .withUsername(usernameMickey)
                .withFirstname(firstnameMickey)
                .withLastname(lastNameMickey)
                .withEmailAddress(validEmailMickey)
                .withPassword(validPasswordMickey)
                .build();

        when(userDao.getUser(usernameMickey)).thenThrow(UserAlreadyExistsException.class);
        when(context.getLogger()).thenReturn(lambdaLogger);

        assertThrows(UserAlreadyExistsException.class, () -> {
            createUserActivity.handleRequest(createUserRequest, context);
        });

    }

    @Test
    public void handleRequest_createUserWithoutLastname_returnsCreateUserResult() {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .withUsername(usernameMickey)
                .withFirstname(firstnameMickey)
                .withEmailAddress(validEmailMickey)
                .withPassword(validPasswordMickey)
                .build();

        when(userDao.saveUser(any(User.class))).then(AdditionalAnswers.returnsFirstArg());
        when(context.getLogger()).thenReturn(lambdaLogger);

        UserModel userModel = UserModel.builder()
                .withUsername(usernameMickey)
                .withFirstname(firstnameMickey)
                .withEmailAddress(validEmailMickey)
                .build();

        when(userModelConverter.toUserModel(any(User.class))).thenReturn(userModel);

        CreateUserResult createUserResult = createUserActivity.handleRequest(createUserRequest, context);

        assertEquals(usernameMickey, createUserResult.getUser().getUsername(),
                "Expected username to be " + usernameMickey);
        assertEquals(firstnameMickey, createUserResult.getUser().getFirstname(),
                "Expected firstname to be " + firstnameMickey);
        assertEquals(validEmailMickey, createUserResult.getUser().getEmailAddress(),
                "Expected Email Address to be " + validEmailMickey);
        assertNull(createUserResult.getUser().getLastname(), "Expected last name to be null");

    }

    @Test
    public void handleRequest_createUserWithInvalidEmailAddress_throwsException() {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .withUsername(usernameMickey)
                .withFirstname(firstnameMickey)
                .withEmailAddress(invalidEmailMickey)
                .withPassword(validPasswordMickey)
                .build();

        when(userDao.saveUser(any(User.class))).then(AdditionalAnswers.returnsFirstArg());
        when(context.getLogger()).thenReturn(lambdaLogger);

        assertThrows(InvalidAttributeValueException.class, () -> {
            createUserActivity.handleRequest(createUserRequest, context);
        });
    }

    @Test
    public void handleRequest_createUserWithInvalidUsername_throwsException() {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .withUsername(invalidUsername)
                .withFirstname(firstnameMickey)
                .withEmailAddress(validEmailMickey)
                .withPassword(validPasswordMickey)
                .build();

        when(userDao.saveUser(any(User.class))).then(AdditionalAnswers.returnsFirstArg());
        when(context.getLogger()).thenReturn(lambdaLogger);

        assertThrows(InvalidAttributeValueException.class, () -> {
            createUserActivity.handleRequest(createUserRequest, context);
        });
    }

    @Test
    public void handleRequest_createUserWithInvalidPassword_throwsException() {
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .withUsername(usernameMickey)
                .withFirstname(firstnameMickey)
                .withLastname(lastNameMickey)
                .withEmailAddress(validEmailMickey)
                .withPassword(invalidPasswordMickey)
                .build();

        when(userDao.saveUser(any(User.class))).then(AdditionalAnswers.returnsFirstArg());
        when(context.getLogger()).thenReturn(lambdaLogger);

        assertThrows(InvalidAttributeValueException.class, () -> {
            createUserActivity.handleRequest(createUserRequest, context);
        });
    }


}

package jobservice.activity;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import jobservice.converters.UserModelConverter;
import jobservice.dynamodb.UserDao;
import jobservice.dynamodb.models.User;
import jobservice.exceptions.UserNotFoundException;
import jobservice.models.UserModel;
import jobservice.models.requests.GetUserRequest;
import jobservice.models.results.GetUserResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GetUserActivityTest {

    private final String usernameMickey = "mickeymouse";
    private final String firstnameMickey = "Mickey";
    private final String lastNameMickey = "Mouse";
    private final String validEmailMickey = "mickey@yahoo.com";
    private final String validPasswordMickey = "Mickey@123";
    private final String usernameNotInDatabase = "donaldduck";

    @Mock
    private UserDao userDao;

    @Mock
    private UserModelConverter userModelConverter;

    @Mock
    private Context context;

    private GetUserActivity getUserActivity;
    private LambdaLogger lambdaLogger;

    @BeforeEach
    public void setup() {
        initMocks(this);
        getUserActivity = new GetUserActivity(userDao, userModelConverter);
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
    public void handleRequest_savedUserFound_returnsGetUserResult() {
        User user = User.builder()
                .withUsername(usernameMickey)
                .withFirstname(firstnameMickey)
                .withLastname(lastNameMickey)
                .withEmailAddress(validEmailMickey)
                .withPassword(validPasswordMickey)
                .build();

        UserModel userModel = UserModel.builder()
                .withUsername(usernameMickey)
                .withFirstname(firstnameMickey)
                .withLastname(lastNameMickey)
                .withEmailAddress(validEmailMickey)
                .build();

        when(userDao.getUser(usernameMickey)).thenReturn(user);
        when(userModelConverter.toUserModel(user)).thenReturn(userModel);
        when(context.getLogger()).thenReturn(lambdaLogger);

        GetUserRequest getUserRequest = GetUserRequest.builder()
                .withUsername(usernameMickey)
                .withPassword(validPasswordMickey)
                .build();

        GetUserResult getUserResult = getUserActivity.handleRequest(getUserRequest, context);

        assertEquals(usernameMickey, getUserResult.getUser().getUsername(),
                "Expected username to be " + usernameMickey);
        assertEquals(firstnameMickey, getUserResult.getUser().getFirstname(),
                "Expected firstname to be " + firstnameMickey);
        assertEquals(lastNameMickey, getUserResult.getUser().getLastname(),
                "Expected lastname to be " + lastNameMickey);
        assertEquals(validEmailMickey, getUserResult.getUser().getEmailAddress(),
                "Expected Email Address to be " + validEmailMickey);
    }

    @Test
    public void handleRequest_userNotInDatabase_throwsUserNotFoundException() {
        User user = User.builder()
                .withUsername(usernameNotInDatabase)
                .withFirstname(firstnameMickey)
                .withLastname(lastNameMickey)
                .withEmailAddress(validEmailMickey)
                .withPassword(validPasswordMickey)
                .build();

        when(userDao.getUser(usernameNotInDatabase)).thenThrow(UserNotFoundException.class);
        when(context.getLogger()).thenReturn(lambdaLogger);

        GetUserRequest getUserRequest = GetUserRequest.builder()
                .withUsername(usernameNotInDatabase)
                .withPassword("Jobtracker@123")
                .build();


        assertThrows(UserNotFoundException.class, () -> {
            getUserActivity.handleRequest(getUserRequest, context);
        });
    }

}

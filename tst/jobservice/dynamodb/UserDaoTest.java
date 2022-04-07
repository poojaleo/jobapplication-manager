package jobservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import jobservice.dynamodb.models.User;
import jobservice.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserDaoTest {

    private final String validUsernameMickey = "mickeymouse";
    private final String firstnameMickey = "Mickey";
    private final String lastNameMickey = "Mouse";
    private final String validEmailMickey = "mickey@yahoo.com";
    private final String validPasswordMickey = "Mickey@123";
    private final String userNotInDatabase = "donaldduck";

    private UserDao userDao;

    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @BeforeEach
    public void setup() {
        initMocks(this);
        userDao = new UserDao(dynamoDBMapper);
    }

    @Test
    public void getUser_validUsername_returnsUser() {

        User user = User.builder()
                .withUsername(validUsernameMickey)
                .withFirstname(firstnameMickey)
                .withLastname(lastNameMickey)
                .withEmailAddress(validEmailMickey)
                .withPassword(validPasswordMickey)
                .build();

        when(dynamoDBMapper.load(User.class, validUsernameMickey)).thenReturn(user);

        User userReturned = userDao.getUser(validUsernameMickey);

        assertEquals(validUsernameMickey, userReturned.getUsername(), "Expected username to be " + validUsernameMickey);
        assertEquals(firstnameMickey, userReturned.getFirstname(), "Expected firstname to be " + firstnameMickey);
        assertEquals(lastNameMickey, userReturned.getLastname(), "Expected lastname to be " + lastNameMickey);
        assertEquals(validEmailMickey, userReturned.getEmailAddress(), "Expected email to be " + validEmailMickey);
        assertEquals(validPasswordMickey, userReturned.getPassword(), "Expected Password to be " + validPasswordMickey);


    }

    @Test
    public void getUser_userNotInDatabase_throwsUserNotFoundException() {

        User user = User.builder()
                .withUsername(userNotInDatabase)
                .withFirstname(firstnameMickey)
                .withEmailAddress(validEmailMickey)
                .withPassword(validPasswordMickey)
                .build();

        when(dynamoDBMapper.load(User.class, userNotInDatabase)).thenThrow(UserNotFoundException.class);

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userDao.getUser(userNotInDatabase);
        });

    }

    @Test
    public void saveUser_validUsername_returnsUser() {

        User user = User.builder()
                .withUsername(validUsernameMickey)
                .withFirstname(firstnameMickey)
                .withLastname(lastNameMickey)
                .withEmailAddress(validEmailMickey)
                .withPassword(validPasswordMickey)
                .build();

        User userReturned = userDao.saveUser(user);

        verify(dynamoDBMapper, times(1)).save(user);

    }
}

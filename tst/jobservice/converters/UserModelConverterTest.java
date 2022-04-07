package jobservice.converters;

import jobservice.dynamodb.models.User;
import jobservice.models.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserModelConverterTest {

    private final String usernameMickey = "mickeymouse";
    private final String firstnameMickey = "Mickey";
    private final String lastNameMickey = "Mouse";
    private final String validEmailMickey = "mickey@yahoo.com";
    private final String validPasswordMickey = "Mickey@123";

    private UserModelConverter userModelConverter;

    @BeforeEach
    public void setup() {
        userModelConverter = new UserModelConverter();
    }

    @Test
    public void toUserModel_withUserLastName_returnsUserModel() {
        User user = User.builder()
                .withUsername(usernameMickey)
                .withFirstname(firstnameMickey)
                .withLastname(lastNameMickey)
                .withEmailAddress(validEmailMickey)
                .withPassword(validPasswordMickey)
                .build();

        UserModel userModel = userModelConverter.toUserModel(user);

        assertEquals(usernameMickey, userModel.getUsername(),
                "Expected username to be " + usernameMickey);
        assertEquals(firstnameMickey, userModel.getFirstname(),
                "Expected firstname to be " + firstnameMickey);
        assertEquals(lastNameMickey, userModel.getLastname(),
                "Expected lastname to be " + lastNameMickey);
        assertEquals(validEmailMickey, userModel.getEmailAddress(),
                "Expected Email Address to be " + validEmailMickey);
    }

    @Test
    public void toUserModel_withNoUserLastName_returnsUserModel() {
        User user = User.builder()
                .withUsername(usernameMickey)
                .withFirstname(firstnameMickey)
                .withEmailAddress(validEmailMickey)
                .withPassword(validPasswordMickey)
                .build();

        UserModel userModel = userModelConverter.toUserModel(user);

        assertEquals(usernameMickey, userModel.getUsername(),
                "Expected username to be " + usernameMickey);
        assertEquals(firstnameMickey, userModel.getFirstname(),
                "Expected firstname to be " + firstnameMickey);
        assertEquals(null, userModel.getLastname(),
                "Expected lastname to be null");
        assertEquals(validEmailMickey, userModel.getEmailAddress(),
                "Expected Email Address to be " + validEmailMickey);
    }

}

package jobservice.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import jobservice.dynamodb.models.User;
import jobservice.exceptions.UserNotFoundException;

import javax.inject.Inject;

public class UserDao {
    private final DynamoDBMapper dynamoDBMapper;

    /**
     * Instantiates a UserDao object.
     * @param dynamoDBMapper the {@link DynamoDBMapper} used to interact with the users table
     */
    @Inject
    public UserDao(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    /**
     * Returns the {@link User} corresponding to the specified username.
     * @param username username of the user
     * @return User object if found in the dynamoDb Table
     * @throws UserNotFoundException if user with corresponding username does not exist
     */

    public User getUser(String username) throws UserNotFoundException {
        User user = this.dynamoDBMapper.load(User.class, username);

        if(user == null) {
            throw new UserNotFoundException(String.format("Could not find user with username: %s", username));
        }

        return user;
    }

    /**
     * Save the provided user in Users DynamoDB table
     * @param user {@link User} that has been created or updated
     * @return User object
     */

    public User saveUser(User user) {
        this.dynamoDBMapper.save(user);
        return user;
    }
}

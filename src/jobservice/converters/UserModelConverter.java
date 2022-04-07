package jobservice.converters;

import jobservice.dynamodb.models.User;
import jobservice.models.UserModel;

import javax.inject.Inject;

public class UserModelConverter {

    @Inject
    public UserModelConverter() {
    }

    /**
     * Converts a provided {@link User} into a {@link UserModel} representation.
     * @param user the user object to convert
     * @return UserModel object
     */
    public UserModel toUserModel(User user) {
        UserModel userModel = UserModel.builder()
                .withUsername(user.getUsername())
                .withFirstname(user.getFirstname())
                .withEmailAddress(user.getEmailAddress())
                .build();


        if(user.getLastname() != null) {
           userModel.setLastname(user.getLastname());
        }

        return userModel;

    }
}

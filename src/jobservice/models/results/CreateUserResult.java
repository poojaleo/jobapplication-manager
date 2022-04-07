package jobservice.models.results;

import jobservice.models.UserModel;
/**
 * Implementation of the CreateUserResult class.
 */
public class CreateUserResult {
    private UserModel userModel;

    public CreateUserResult(Builder builder) {
        this.userModel = builder.userModel;
    }

    public UserModel getUser() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Implementation of the CreateUserResult Builder class.
     */
    public static final class Builder {
        private UserModel userModel;

        public Builder withUser(UserModel userModelToUse) {
            this.userModel = userModelToUse;
            return this;
        }

        public CreateUserResult build() {
            return new CreateUserResult(this);
        }
    }

}

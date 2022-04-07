package jobservice.models.results;

import jobservice.models.UserModel;
/**
 * Implementation of the GetUserResult class.
 */
public class GetUserResult {
    private UserModel userModel;

    public GetUserResult(Builder builder) {
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
     * Implementation of the GetUserResult Builder class.
     */
    public static final class Builder {
        private UserModel userModel;

        public Builder withUser(UserModel userModelToUse) {
            this.userModel = userModelToUse;
            return this;
        }

        public GetUserResult build() {
            return new GetUserResult(this);
        }
    }
}

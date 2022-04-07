package jobservice.models.results;

import jobservice.models.UserModel;
/**
 * Implementation of the UpdateUserResult class.
 */
public class UpdateUserResult {
    private UserModel userModel;

    public UpdateUserResult(Builder builder) {
        this.userModel = builder.userModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Implementation of the UpdateUserResult Builder class.
     */
    public static final class Builder {
        private UserModel userModel;

        public Builder() {
        }

        public Builder withUser(UserModel userModelToUse) {
            this.userModel = userModelToUse;
            return this;
        }

        public UpdateUserResult build() {
            return new UpdateUserResult(this);
        }
    }
}


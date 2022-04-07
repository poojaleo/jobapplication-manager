package jobservice.models.requests;

import java.util.Objects;

/**
 * Implementation of the GetUserRequest class.
 */
public class GetUserRequest {
    private String username;
    private String password;

    public GetUserRequest() {
    }

    public GetUserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public GetUserRequest(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetUserRequest that = (GetUserRequest) o;
        return username.equals(that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "GetUserRequest{" +
                "username='" + username + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Implementation of the GetUserRequest Builder class.
     */
    public static final class Builder {
        private String username;
        private String password;

        public Builder() {
        }

        public Builder withUsername(String usernameToUse) {
            this.username = usernameToUse;
            return this;
        }

        public Builder withPassword(String passwordToUse) {
            this.password = passwordToUse;
            return this;
        }

        public GetUserRequest build() {
            return new GetUserRequest(this);
        }
    }
}

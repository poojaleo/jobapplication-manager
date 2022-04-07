package jobservice.models.requests;

import java.util.Objects;

public class GetAllQuestionsRequest {
    private String username;
    private boolean onlyNeedsWork;

    public GetAllQuestionsRequest() {}

    public GetAllQuestionsRequest(String username, boolean onlyNeedsWork) {
        this.username = username;
        this.onlyNeedsWork = onlyNeedsWork;
    }

    public GetAllQuestionsRequest (Builder builder) {
        this.username = builder.username;
        this.onlyNeedsWork = builder.onlyNeedsWork;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isOnlyNeedsWork() {
        return onlyNeedsWork;
    }

    public void setOnlyNeedsWork(boolean onlyNeedsWork) {
        this.onlyNeedsWork = onlyNeedsWork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetAllQuestionsRequest that = (GetAllQuestionsRequest) o;
        return isOnlyNeedsWork() == that.isOnlyNeedsWork() && Objects.equals(getUsername(), that.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), isOnlyNeedsWork());
    }

    @Override
    public String toString() {
        return "GetAllQuestionsRequest{" +
                "username='" + username + '\'' +
                ", onlyNeedsWork=" + onlyNeedsWork +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String username;
        private boolean onlyNeedsWork;

        public Builder withUsername(String usernameToUse) {
            this.username = usernameToUse;
            return this;
        }

        public Builder withOnlyNeedsWork(boolean onlyNeedsWorkToUse) {
            this.onlyNeedsWork = onlyNeedsWorkToUse;
            return this;
        }

        public GetAllQuestionsRequest build() {
            return new GetAllQuestionsRequest(this);
        }
    }
}

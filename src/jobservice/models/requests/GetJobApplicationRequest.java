package jobservice.models.requests;

import java.util.Objects;

public class GetJobApplicationRequest {
    private String username;
    private String applicationId;

    public GetJobApplicationRequest() {

    }

    public GetJobApplicationRequest(Builder builder) {
        this.username = builder.username;
        this.applicationId = builder.applicationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetJobApplicationRequest that = (GetJobApplicationRequest) o;
        return getUsername().equals(that.getUsername()) && getApplicationId().equals(that.getApplicationId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getApplicationId());
    }

    @Override
    public String toString() {
        return "GetJobApplicationRequest{" +
                "username='" + username + '\'' +
                ", applicationId='" + applicationId + '\'' +
                '}';
    }

    public static Builder builder() { return new Builder();}

    public static final class Builder {
        private String username;
        private String applicationId;

        public Builder(){
        }

        public Builder withUsername(String usernameToUse) {
            this.username = usernameToUse;
            return this;
        }

        public Builder withApplicationId(String applicationIdToUse) {
            this.applicationId = applicationIdToUse;
            return this;
        }

        public GetJobApplicationRequest build() { return new GetJobApplicationRequest(this);}
    }

}

package jobservice.models.requests;

import java.util.Objects;

public class GetAllJobApplicationsRequest {
    private String username;

    public GetAllJobApplicationsRequest(){
    }

    public GetAllJobApplicationsRequest(Builder builder) {
        this.username = builder.username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetAllJobApplicationsRequest that = (GetAllJobApplicationsRequest) o;
        return Objects.equals(getUsername(), that.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }

    @Override
    public String toString() {
        return "GetAllJobApplicationsRequest{" +
                "username='" + username + '\'' +
                '}';
    }

    public static Builder builder() { return new Builder();}

    public static final class Builder {
        private String username;

        public Builder(){
        }

        public Builder withUsername(String usernameToUse) {
            this.username = usernameToUse;
            return this;
        }

        public GetAllJobApplicationsRequest build() { return new GetAllJobApplicationsRequest(this);}
    }

}

package jobservice.models.requests;

import java.util.Objects;
/**
 * Implementation of the CreateUserRequest class.
 */
public class CreateUserRequest {

    private String username;
    private String firstname;
    private String lastname;
    private String emailAddress;
    private String password;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String username, String firstname, String lastname, String emailAddress, String password) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public CreateUserRequest(String username, String firstname, String emailAddress, String password) {
        this.username = username;
        this.firstname = firstname;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public CreateUserRequest(Builder builder) {
        this.username = builder.username;
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.emailAddress = builder.emailAddress;
        this.password = builder.password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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
        CreateUserRequest that = (CreateUserRequest) o;
        return username.equals(that.username) && firstname.equals(that.firstname) && lastname.equals(that.lastname) && emailAddress.equals(that.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, firstname, lastname, emailAddress);
    }

    @Override
    public String toString() {
        return "CreateUserRequest{" +
                "username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Implementation of the CreateUserRequest Builder class.
     */
    public static final class Builder {
        private String username;
        private String firstname;
        private String lastname;
        private String emailAddress;
        private String password;

        public Builder() {
        }

        public Builder withUsername(String usernameToUse) {
            this.username = usernameToUse;
            return this;
        }

        public Builder withFirstname(String firstnameToUse) {
            this.firstname = firstnameToUse;
            return this;
        }

        public Builder withLastname(String lastnameToUse) {
            this.lastname = lastnameToUse;
            return this;
        }

        public Builder withEmailAddress(String emailAddressToUse) {
            this.emailAddress = emailAddressToUse;
            return this;
        }

        public Builder withPassword(String passwordToUse) {
            this.password = passwordToUse;
            return this;
        }

        public CreateUserRequest build() {
            return new CreateUserRequest(this);
        }


    }
}

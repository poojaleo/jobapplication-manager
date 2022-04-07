package jobservice.models;

import java.util.Objects;

/**
 * Implementation of the UserModel class.
 */
public class UserModel {
    private String username;
    private String firstname;
    private String lastname;
    private String emailAddress;

    public UserModel() {
    }

    public UserModel(Builder builder) {
        this.username = builder.username;
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.emailAddress =  builder.emailAddress;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equals(username, userModel.username) && Objects.equals(firstname, userModel.firstname) &&
                Objects.equals(lastname, userModel.lastname) && Objects.equals(emailAddress, userModel.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, firstname, lastname, emailAddress);
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Implementation of the UserModel Builder class.
     */
    public static final class Builder {
        private String username;
        private String firstname;
        private String lastname;
        private String emailAddress;

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

        public UserModel build() {
            return new UserModel(this);
        }
    }
}

package jobservice.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JobTrackerServiceUtilityTest {

    @Test
    void isValidString_emptyString_returnsFalse() {
        // GIVEN
        String emptyString = "";

        // WHEN
        boolean result = JobTrackerServiceUtility.isValidString(emptyString);

        // THEN
        assertFalse(result, "Expected empty string to return false: " + emptyString);
    }

    @Test
    void isValidString_invalidCharacters_returnsFalse() {
        // GIVEN
        String invalidCharacters = "'\\'";

        // WHEN
        boolean result = JobTrackerServiceUtility.isValidString(invalidCharacters);

        // THEN
        assertFalse(result, "Expected string with invalidCharacters to return false: " + invalidCharacters);
    }

    @Test
    void isValidString_validCharacters_returnsTrue() {
        // GIVEN
        String validString = "valid";

        // WHEN
        boolean result = JobTrackerServiceUtility.isValidString(validString);

        // THEN
        assertTrue(result, "Expected string with valid characters to return true: " + validString);
    }

    @Test
    void isValidEmailAddress_emptyString_returnsFalse() {
        // GIVEN
        String emptyEmailAddress = "";

        // WHEN
        boolean result = JobTrackerServiceUtility.isValidEmailAddress(emptyEmailAddress);

        // THEN
        assertFalse(result, "Expected empty email address to return false: " + emptyEmailAddress);
    }

    @Test
    void isValidEmailAddress_invalidEmailAddress_returnsFalse() {
        // GIVEN
        String invalidEmailAddress= "gmail.com@email";

        // WHEN
        boolean result = JobTrackerServiceUtility.isValidEmailAddress(invalidEmailAddress);

        // THEN
        assertFalse(result, "Expected string with an invalid Email Address to return false: " + invalidEmailAddress);
    }

    @Test
    void isValidEmailAddress_validEmailAddress_returnsTrue() {
        // GIVEN
        String validEmailAddress = "email@gmail.com";

        // WHEN
        boolean result = JobTrackerServiceUtility.isValidEmailAddress(validEmailAddress);

        // THEN
        assertTrue(result, "Expected string with a validEmailAddress to return true: " + validEmailAddress);
    }

    @Test
    void isValidPassword_emptyString_returnsFalse() {
        // GIVEN
        String emptyPassword = "";

        // WHEN
        boolean result = JobTrackerServiceUtility.isValidPassword(emptyPassword);

        // THEN
        assertFalse(result, "Expected empty password to return false: " + emptyPassword);
    }

    @Test
    void isValidPassword_invalidPassword_returnsFalse() {
        // GIVEN
        String invalidPassword= "hacked";

        // WHEN
        boolean result = JobTrackerServiceUtility.isValidPassword(invalidPassword);

        // THEN
        assertFalse(result, "Expected string with an invalid Password to return false: " + invalidPassword);
    }

    @Test
    void isValidPassword_validPassword_returnsTrue() {
        // GIVEN
        String validPassword = "$ecur1Ty";

        // WHEN
        boolean result = JobTrackerServiceUtility.isValidPassword(validPassword);

        // THEN
        assertTrue(result, "Expected string with a valid Password to return true: " + validPassword);
    }

    @Test
    void generateId_returnsRandomTenCharacterString() {
        // WHEN
        String id = JobTrackerServiceUtility.generateId();

        // THEN
        assertEquals(10, id.length(), "Expected random ID length to be 10 characters.");
    }
}

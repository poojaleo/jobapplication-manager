package jobservice.util;

import org.junit.platform.commons.util.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;


import java.util.regex.Pattern;

public class JobTrackerServiceUtility {
    private static final Pattern INVALID_CHARACTER_PATTERN = Pattern.compile("[\"\'\\\\]");
    private static final Pattern VALID_EMAIL_PATTERN = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$");
    // Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character
    private static final Pattern VALID_PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    private static final int ID_LENGTH = 10;

    // Don't instantiate
    private JobTrackerServiceUtility() {}

    public static boolean isValidString(final String stringToValidate) {
        if (StringUtils.isBlank(stringToValidate)) {
            return false;
        }

        return !INVALID_CHARACTER_PATTERN.matcher(stringToValidate).find();
    }

    public static boolean isValidEmailAddress(final String emailToValidate) {
        if(StringUtils.isBlank(emailToValidate)) {
            return false;
        }

        return VALID_EMAIL_PATTERN.matcher(emailToValidate).find();
    }

    public static boolean isValidPassword(final String passwordToValidate) {
        if(StringUtils.isBlank(passwordToValidate)) {
            return false;
        }

        return VALID_PASSWORD_PATTERN.matcher(passwordToValidate).find();
    }

    public static String generateId() {
        return RandomStringUtils.randomAlphanumeric(ID_LENGTH);
    }
}

package com.bcp.yamaha.util;

import java.util.regex.Pattern;

public class ValidationUtil {
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PHONE_REGEX = "^[6-9]\\d{9}$";
    private static final String USERNAME_REGEX = "^([A-Z][a-z]{0,19})(\\s[A-Z][a-z]{0,19}){0,4}$";
    private static final String DL_REGEX = "^[A-Z]{2}[0-9]{2}[0-9]{4}[0-9]{7}$";

    public static boolean isValidPassword(String password) {
        return password != null && Pattern.matches(PASSWORD_REGEX, password);
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber == null || !phoneNumber.matches(PHONE_REGEX);
    }

    public static boolean isValidUserName(String userName) {
        return userName != null && userName.matches(USERNAME_REGEX);
    }
    public static boolean isValidDl(String dlNo) {
        return dlNo == null || !dlNo.matches(DL_REGEX);
    }
}

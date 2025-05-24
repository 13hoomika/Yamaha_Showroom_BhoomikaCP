package com.bcp.yamaha.util;

public class ValidationUtil {
    private static final String PHONE_REGEX = "^[6-9]\\d{9}$";
    private static final String USERNAME_REGEX = "^([A-Z][a-z]{0,19})(\\s[A-Z][a-z]{0,19}){0,4}$";
    private static final String DL_REGEX = "^[A-Z]{2}[0-9]{2}[0-9]{4}[0-9]{7}$";

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches(PHONE_REGEX);
    }

    public static boolean isValidUserName(String userName) {
        return userName != null && userName.matches(USERNAME_REGEX);
    }
    public static boolean isValidDl(String dlNo) {
        return dlNo != null && dlNo.matches(DL_REGEX);
    }
}

package com.bcp.yamaha.util;

import java.util.regex.Pattern;

public class ValidationUtil {
    //Constants
    private static final int MAX_FIELD_LENGTH = 25;

    //Regex Patterns
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String EMAIL_REGEX = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$";
    private static final String PHONE_REGEX = "^[6-9]\\d{9}$";
    private static final String NAME_REGEX = "^([A-Z][a-z]{0,19})(\\s[A-Z][a-z]{0,19}){0,4}$";
    private static final String DL_REGEX = "^[A-Z]{2}[0-9]{2}[0-9]{4}[0-9]{7}$";

    private static final String BIKE_MODEL_REGEX =
            // Base model (e.g., "R15", "FZ-S") & Version (e.g., "V4", "Ver 2.0") Suffixes
            "^[A-Za-z0-9]+(?:[- ][A-Za-z0-9]+)*\\s(?:V\\d+|Ver\\s\\d+(?:\\.\\d+)?|FI|DLX|Hybrid|S|FI\\sHybrid|DLX\\sVer\\s\\d+(?:\\.\\d+)?)?$";

    // === Validation Methods ===
    public static boolean isValidPassword(String password) {
        return password != null && Pattern.matches(PASSWORD_REGEX, password);
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    // FIXED: Replaced `||` with `&&` (bug in original code)
    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches(PHONE_REGEX);
    }

    public static boolean isValidUserName(String userName) {
        return userName != null && userName.matches(NAME_REGEX);
    }

    // FIXED: Replaced `||` with `&&` (bug in original code)
    public static boolean isValidDl(String dlNo) {
        return dlNo != null && dlNo.matches(DL_REGEX);
    }

    public static boolean isValidShowroomName(String showroomName) {
        return showroomName != null && showroomName.matches(NAME_REGEX);
    }

    public static String validateBikeModel(String bikeModel) {
        if (bikeModel == null) return "Bike model cannot be null";
        if (bikeModel.length() > MAX_FIELD_LENGTH) return "Bike model cannot exceed  " + MAX_FIELD_LENGTH + " characters";
        if (!bikeModel.matches(BIKE_MODEL_REGEX)) return "Invalid bike model format";
        return null;
    }
    public static String validateBikeColor(String bikeColor) {
        if (bikeColor == null || bikeColor.trim().isEmpty()) {
            return "Bike color cannot be empty.";
        }
        if (bikeColor.length() > MAX_FIELD_LENGTH) {
            return "Bike color cannot exceed " + MAX_FIELD_LENGTH +" characters.";
        }
        if (!Pattern.matches(NAME_REGEX, bikeColor)) { // NAME_REGEX assumes "Firstname Lastname" format
            return "Invalid color format. Please use standard characters (e.g., Red, Matte Black).";
        }
        return null;
    }

}

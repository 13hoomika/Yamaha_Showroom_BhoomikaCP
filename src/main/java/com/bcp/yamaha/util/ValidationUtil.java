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
    private static final String DL_REGEX = "^[A-Z]{2}\\d{2}\\d{4}\\d{7}$";
//    private static final String DL_REGEX = "^[A-Z]{2}[0-9]{2}[0-9]{4}[0-9]{7}$";

    /*private static final String BIKE_MODEL_REGEX =
            "^([A-Z][a-z0-9]{0,19})(?:[- ]([A-Z]{2,}|[A-Z][a-z0-9]{0,19}|\\d{1,4})){0,5}(?:\\s(V\\d+(\\.\\d+)?|Ver\\s\\d+(\\.\\d+)?|FI(\\sHybrid)?|DLX(\\sVer\\s\\d+(\\.\\d+)?)?|Hybrid|S))?$";
*/

    private static final String BIKE_MODEL_REGEX =
            "^[A-Z][A-Za-z0-9]*(?:[- ][A-Z][A-Za-z0-9]*|[- ]\\d+(?:\\.\\d+)?){0,6}$";


    // === Validation Methods ===
    public static String validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return "Please enter a password.";
        }
        if (!Pattern.matches(PASSWORD_REGEX, password)) {
            return "Password must be at least 8 characters long, and include at least one uppercase letter, one lowercase letter, one number, and one special character (@$!%*?&).";
        }
        return null;//valid
    }

    public static String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Please enter a name";
        }
        if (name.length() > MAX_FIELD_LENGTH) {
            return "Name cannot exceed " + MAX_FIELD_LENGTH + " characters";
        }
        if (!name.matches(NAME_REGEX)) {  // NAME_REGEX assumes "Firstname Lastname" format
            return "Name must start with capital letter and contain only letters (e.g., John Doe)";
        }
        return null;
    }

    public static String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return "Please enter an email address.";
        }

        if (!email.matches(EMAIL_REGEX)) {
            return "Please enter a valid email address using only lowercase letters (e.g., user@example.com)";
        }
        return null;
    }

    public static String validPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return "Please enter a phone number.";
        }

        if (!phoneNumber.matches(PHONE_REGEX)) {
            return "Please enter a valid  10-digit phone number starting with 6, 7, 8, or 9.";
        }
        return null;
    }

    public static String validateDl(String dlNo) {
        if (dlNo == null || dlNo.trim().isEmpty()) {
            return "Please enter your Driving License number";
        }
        if (dlNo.length() > MAX_FIELD_LENGTH) {
            return "Driving License number is too long (max " + MAX_FIELD_LENGTH + " characters)";
        }
        dlNo = dlNo.trim().toUpperCase(); // Normalize input
        if (!dlNo.matches(DL_REGEX)) {
            return "Please enter a valid Driving License number. It should be 15" +
                    " characters long and follow the pattern: 2 letters (state code), 2 digits (year), " +
                    "4 digits (unique number), and 7 digits (personal ID). (e.g., KA0120231234567)";
            /*return "Please enter a valid Driving License number. It should be " + DL_NUMBER_LENGTH + " characters long.\n" +
                    "• First 2 characters: State code (e.g., KA for Karnataka)\n" +
                    "• Next 2 digits: Issuance year (e.g., 01 for 2001)\n" +
                    "• Next 4 digits: Unique number\n" +
                    "• Last 7 digits: Personal identification number\n" +
                    " Example: KA0120231234567";*/
        }
        return null;
    }

    public static String validShowroomName(String showroomName) {
        if (showroomName == null || showroomName.trim().isEmpty()) {
            return "Please enter a showroom name";
        }
        if (showroomName.length() > MAX_FIELD_LENGTH) {
            return "Name cannot exceed " + MAX_FIELD_LENGTH + " characters";
        }
        if (!showroomName.matches(NAME_REGEX)) {
            return "Name must start with capital letter and contain only letters (e.g., Yamaha Motors)";
        }
        return null;
    }

    public static String validateBikeModel(String bikeModel) {
        if (bikeModel == null)
            return "Bike model cannot be null";
        if (bikeModel.length() > MAX_FIELD_LENGTH)
            return "Bike model cannot exceed " + MAX_FIELD_LENGTH + " characters";
        if (!bikeModel.matches(BIKE_MODEL_REGEX))
            return "Invalid bike model. (e.g., R15, Ray ZR 125, FZ-S FI Ver 4.0)";
        return null;
    }

    public static String validateBikeColor(String bikeColor) {
        if (bikeColor == null || bikeColor.trim().isEmpty()) {
            return "Bike color cannot be empty.";
        }
        if (bikeColor.length() > MAX_FIELD_LENGTH) {
            return "Bike color cannot exceed " + MAX_FIELD_LENGTH +" characters.";
        }
        if (!Pattern.matches(NAME_REGEX, bikeColor)) {
            return "Invalid color format. Please use standard characters (e.g., Red, Matte Black).";
        }
        return null;
    }
}

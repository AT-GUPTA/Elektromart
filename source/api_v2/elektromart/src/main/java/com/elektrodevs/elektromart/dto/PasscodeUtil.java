package com.elektrodevs.elektromart.dto;

public class PasscodeUtil {
    public static String Encrypt(String passcode) {
        if (passcode == null || passcode.isEmpty()) {
            return "";
        }

        char firstChar = passcode.charAt(0);

        String stars = "*".repeat(passcode.length() - 1);

        return firstChar + stars;
    }
}

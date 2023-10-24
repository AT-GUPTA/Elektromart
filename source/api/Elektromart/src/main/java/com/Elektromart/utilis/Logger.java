package com.elektromart.utilis;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    public static void info(String message) {
        log("INFO", message, "\u001B[97m"); // White text
    }

    public static void warning(String message) {
        log("WARNING", message, "\u001B[93m"); // Yellow text
    }

    public static void error(String message) {
        log("ERROR", message, "\u001B[91m"); // Red text
    }

    private static void log(String level, String message, String colorCode) {
        String formattedMessage = String.format("[%s] %s: %s", getCurrentTime(), level, message);
        System.out.println(colorCode + formattedMessage + "\u001B[0m");
    }

    private static String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(new Date());
    }
}

package com.bcp.yamaha.util;

import java.time.Duration;

public class FormatUtil {
    public static String capitalize(String input) {
        if (input == null || input.isEmpty()) return input;
        input = input.trim().toLowerCase();
        return Character.toUpperCase(input.charAt(0)) + input.substring(1);
    }

    public static String capitalizeWords(String input) {
        if (input == null || input.isEmpty()) return input;
        String[] words = input.trim().toLowerCase().split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }
        return result.toString().trim();
    }

    public static String formatDuration(Duration duration) {
        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();

        if (minutes > 0) {
            return String.format("%d minute%s %d second%s",
                    minutes, minutes != 1 ? "s" : "",
                    seconds, seconds != 1 ? "s" : "");
        }
        return String.format("%d second%s", seconds, seconds != 1 ? "s" : "");
    }

}


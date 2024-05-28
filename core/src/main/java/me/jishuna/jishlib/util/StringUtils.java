package me.jishuna.jishlib.util;

public final class StringUtils {

    public static String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    public static String capitalizeAll(String input) {
        String[] parts = input.split(" ");
        StringBuilder builder = new StringBuilder();

        for (String part : parts) {
            builder.append(capitalize(part));
            builder.append(" ");
        }
        return builder.toString().trim();
    }

    public static boolean containsIgnoreCase(String input, String match) {
        if (input == null || match == null) {
            return false;
        }

        final int length = match.length();
        if (length == 0) {
            return true;
        }

        for (int i = input.length() - length; i >= 0; i--) {
            if (input.regionMatches(true, i, match, 0, length)) {
                return true;
            }
        }
        return false;
    }

    private StringUtils() {
    }
}

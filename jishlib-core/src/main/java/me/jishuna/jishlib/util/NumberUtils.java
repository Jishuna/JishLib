package me.jishuna.jishlib.util;

/**
 * Utility methods for numbers.
 */
public class NumberUtils {
    private NumberUtils() {
    }

    /**
     * Clamps a value between a minimum and maximum number. <br>
     * The returned value will be between {@code min} and {@code max} inclusive.
     *
     * @param value the value to clamp
     * @param min   the minimum number
     * @param max   the maximum number
     * @return the value clamped to the given minimum and maximum number
     */
    public static double clamp(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }

    /**
     * Clamps a value between a minimum and maximum number. <br>
     * The returned value will be between {@code min} and {@code max} inclusive.
     *
     * @param value the value to clamp
     * @param min   the minimum number
     * @param max   the maximum number
     * @return the value clamped to the given minimum and maximum number
     */
    public static float clamp(float value, float min, float max) {
        return Math.min(Math.max(value, min), max);
    }

    /**
     * Clamps a value between a minimum and maximum number. <br>
     * The returned value will be between {@code min} and {@code max} inclusive.
     *
     * @param value the value to clamp
     * @param min   the minimum number
     * @param max   the maximum number
     * @return the value clamped to the given minimum and maximum number
     */
    public static int clamp(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }

    /**
     * Clamps a value between a minimum and maximum number. <br>
     * The returned value will be between {@code min} and {@code max} inclusive.
     *
     * @param value the value to clamp
     * @param min   the minimum number
     * @param max   the maximum number
     * @return the value clamped to the given minimum and maximum number
     */
    public static long clamp(long value, long min, long max) {
        return Math.min(Math.max(value, min), max);
    }

    /**
     * Clamps a value between a minimum and maximum number. <br>
     * The returned value will be between {@code min} and {@code max} inclusive.
     *
     * @param value the value to clamp
     * @param min   the minimum number
     * @param max   the maximum number
     * @return the value clamped to the given minimum and maximum number
     */
    public static short clamp(short value, short min, short max) {
        return (short) Math.min(Math.max(value, min), max);
    }

    /**
     * Checks if a value is bewteen two numbers,
     *
     * @param value the value to check
     * @param min   the minimum value (inclusive)
     * @param max   the maximum value (inclusive)
     * @return true if the value is between {@code min} and {@code max}
     */
    public static boolean isWithinBounds(int value, int min, int max) {
        return value >= min && value <= max;
    }
}

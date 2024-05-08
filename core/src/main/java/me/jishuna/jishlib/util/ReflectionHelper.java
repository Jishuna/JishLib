package me.jishuna.jishlib.util;

public final class ReflectionHelper {
    public static boolean hasClass(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private ReflectionHelper() {
    }
}

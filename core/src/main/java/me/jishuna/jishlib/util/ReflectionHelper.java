package me.jishuna.jishlib.util;

import java.lang.reflect.Field;

public final class ReflectionHelper {

    public static boolean hasClass(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static Class<?> getClass(String name) {
        try {
            return Class.forName(name);
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    public static Field getField(Class<?> clazz, String name) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    public static Field getField(Class<?> clazz, Class<?> type, int index) {
        int i = 0;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType() == type) {
                if (index == i) {
                    field.setAccessible(true);
                    return field;
                }
                i++;
            }
        }
        return null;
    }

    private ReflectionHelper() {
    }
}

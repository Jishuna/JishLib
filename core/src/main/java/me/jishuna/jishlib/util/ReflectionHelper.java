package me.jishuna.jishlib.util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import org.bukkit.Bukkit;

public final class ReflectionHelper {
    public static final String CRAFTBUKKIT_PACKAGE = Bukkit.getServer().getClass().getPackageName();
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    public static Class<?> getClass(String name) {
        try {
            return Class.forName(name);
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    public static Class<?> getCraftClass(String name) {
        try {
            return Class.forName(CRAFTBUKKIT_PACKAGE + name);
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

    public static MethodHandle getConstructor(Class<?> clazz, Class<?>... parameters) {
        try {
            final Constructor<?> constructor = clazz.getDeclaredConstructor(parameters);
            constructor.setAccessible(true);
            return LOOKUP.unreflectConstructor(constructor);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object readField(Field field, Object instance) {
        try {
            field.setAccessible(true);
            return field.get(instance);
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    public static boolean setField(Field field, Object value, Object instance) {
        try {
            field.setAccessible(true);
            field.set(instance, value);
            return true;
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }

    private ReflectionHelper() {
    }
}

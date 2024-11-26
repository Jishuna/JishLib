package me.jishuna.jishlib.reflection;

import org.bukkit.Bukkit;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

public class ReflectionHelper {
    public static final String CRAFTBUKKIT_PACKAGE = Bukkit.getServer().getClass().getPackageName();
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    public static <T> FieldAccess<T> getField(Class<?> target, Class<T> type, int index) throws ReflectionException {
        try {
            for (Field field : target.getDeclaredFields()) {
                if (type.isAssignableFrom(field.getType()) && index-- <= 0) {
                    field.setAccessible(true);
                    return new FieldAccess<>(type, field);
                }
            }
        } catch (Exception e) {
            throw new ReflectionException("Cannot find field with type " + type, e);
        }

        if (target.getSuperclass() != null) {
            return getField(target.getSuperclass(), type, index);
        }

        throw new ReflectionException("Cannot find field with type " + type);
    }

    public static <T> FieldAccess<T> getField(Class<?> target, Class<T> type, String name) throws ReflectionException {
        try {
            Field field = target.getDeclaredField(name);
            if (type.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                return new FieldAccess<>(type, field);
            }
        } catch (Exception e) {
            throw new ReflectionException("Cannot find field with type " + type, e);
        }
        throw new ReflectionException("Cannot find field with type " + type);
    }

    public static Class<?> getClass(String... names) throws ReflectionException {
        for (String name : names) {
            try {
                return Class.forName(name);
            } catch (ReflectiveOperationException ignored) {
            }
        }

        throw new ReflectionException("Cannot find class with name(s) " + String.join(",", names));
    }

    public static Class<?> getCraftClass(String name) throws ReflectionException {
        return getClass(CRAFTBUKKIT_PACKAGE + name);
    }
}

package me.jishuna.jishlib.nms;

import org.bukkit.Bukkit;

public class ReflectionUtils {
    private static final String CRAFTBUKKIT_PACKAGE = Bukkit.getServer().getClass().getPackage().getName();

    public static Class<?> getCraftBukkitClass(String clazz) {
        try {
            return Class.forName(CRAFTBUKKIT_PACKAGE + "." + clazz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not find CraftBukkit class: " + clazz, e);
        }
    }

    private ReflectionUtils() {
    }
}

package me.jishuna.jishlib;

import java.util.logging.Level;

public class Logger {
    private static final java.util.logging.Logger LOGGER = Plugin.getInstance().getLogger();

    public static void error(String format, Object... args) {
        LOGGER.log(Level.SEVERE, format, args);
    }

    public static void warn(String format, Object... args) {
        LOGGER.log(Level.WARNING, format, args);
    }

    public static void info(String format, Object... args) {
        LOGGER.log(Level.INFO, format, args);
    }
}

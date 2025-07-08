package me.jishuna.jishlib.util;

import me.jishuna.jishlib.JishLib;

import java.util.logging.Level;

public class Logger {

    public static void error(String format, Object... args) {
        JishLib.logger().log(Level.SEVERE, format, args);
    }

    public static void warn(String format, Object... args) {
        JishLib.logger().log(Level.WARNING, format, args);
    }

    public static void info(String format, Object... args) {
        JishLib.logger().log(Level.INFO, format, args);
    }
}
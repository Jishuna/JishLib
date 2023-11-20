package me.jishuna.jishlib.logging;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileLogger {
    private static FileLogger INSTANCE;

    private final PrintWriter writer;
    private final Logger parent;
    private final AtomicBoolean dirty = new AtomicBoolean(false);

    private FileLogger(Logger parent, File target) {
        this.parent = parent;
        this.writer = setupWriter(target);
    }

    public static void initialize(Logger parent, File target) {
        if (INSTANCE == null) {
            INSTANCE = new FileLogger(parent, target);
        }
    }

    public static void close() {
        INSTANCE.writer.close();
    }

    public static void error(String message) {
        log(Level.SEVERE, message, false);
    }

    public static void error(String message, boolean sendToConsole) {
        log(Level.SEVERE, message, sendToConsole);
    }

    public static void warning(String message) {
        log(Level.WARNING, message, false);
    }

    public static void warning(String message, boolean sendToConsole) {
        log(Level.WARNING, message, sendToConsole);
    }

    public static void info(String message) {
        log(Level.INFO, message, false);
    }

    public static void info(String message, boolean sendToConsole) {
        log(Level.INFO, message, sendToConsole);
    }

    public static void log(Level level, String message) {
        log(level, message, false);
    }

    public static void log(Level level, String message, boolean sendToConsole) {
        INSTANCE.writer.printf("[%tT] [%s/%s]: %s%n", Calendar.getInstance(), Thread.currentThread().getName(), level.getName(), message);
        INSTANCE.dirty.set(true);

        if (sendToConsole) {
            INSTANCE.parent.log(level, message);
        }
    }

    public static void flushIfDirty() {
        if (INSTANCE.dirty.get()) {
            INSTANCE.writer.flush();
        }
    }

    private PrintWriter setupWriter(File target) {
        try {
            return new PrintWriter(target);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package me.jishuna.jishlib.util;

import me.jishuna.jishlib.SpigotPlugin;

public final class Tasks {
    public static void run(Runnable task) {
        SpigotPlugin.getInstance().run(task);
    }

    public static void runAsync(Runnable task) {
        SpigotPlugin.getInstance().runAsync(task);
    }

    public static void runLater(Runnable task, int ticks) {
        SpigotPlugin.getInstance().runLater(task, ticks);
    }

    public static void runLaterAsync(Runnable task, int ticks) {
        SpigotPlugin.getInstance().runLaterAsync(task, ticks);
    }

    public static void runTimer(Runnable task, int ticks) {
        runTimer(task, 0, ticks);
    }

    public static void runTimer(Runnable task, int delay, int ticks) {
        SpigotPlugin.getInstance().runTimer(task, delay, ticks);
    }

    public static void runTimerAsync(Runnable task, int ticks) {
        runTimerAsync(task, 0, ticks);
    }

    public static void runTimerAsync(Runnable task, int delay, int ticks) {
        SpigotPlugin.getInstance().runTimerAsync(task, delay, ticks);
    }

    private Tasks() {
    }
}

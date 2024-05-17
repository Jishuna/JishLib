package me.jishuna.jishlib.util;

import me.jishuna.jishlib.Plugin;

public final class Tasks {
    public static void run(Runnable task) {
        Plugin.getInstance().run(task);
    }

    public static void runAsync(Runnable task) {
        Plugin.getInstance().runAsync(task);
    }

    public static void runLater(Runnable task, int ticks) {
        Plugin.getInstance().runLater(task, ticks);
    }

    public static void runLaterAsync(Runnable task, int ticks) {
        Plugin.getInstance().runLaterAsync(task, ticks);
    }

    public static void runTimer(Runnable task, int ticks) {
        runTimer(task, 0, ticks);
    }

    public static void runTimer(Runnable task, int delay, int ticks) {
        Plugin.getInstance().runTimer(task, delay, ticks);
    }

    public static void runTimerAsync(Runnable task, int ticks) {
        runTimerAsync(task, 0, ticks);
    }

    public static void runTimerAsync(Runnable task, int delay, int ticks) {
        Plugin.getInstance().runTimerAsync(task, delay, ticks);
    }

    private Tasks() {
    }
}

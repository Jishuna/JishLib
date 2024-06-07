package me.jishuna.jishlib.util;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import org.bukkit.scheduler.BukkitTask;
import me.jishuna.jishlib.Plugin;

public final class Tasks {
    public static BukkitTask run(Runnable task) {
        return Plugin.getInstance().run(task);
    }

    public static BukkitTask runAsync(Runnable task) {
        return Plugin.getInstance().runAsync(task);
    }

    public static BukkitTask runLater(Runnable task, int ticks) {
        return Plugin.getInstance().runLater(task, ticks);
    }

    public static BukkitTask runLaterAsync(Runnable task, int ticks) {
        return Plugin.getInstance().runLaterAsync(task, ticks);
    }

    public static BukkitTask runTimer(Runnable task, int ticks) {
        return runTimer(task, 0, ticks);
    }

    public static BukkitTask runTimer(Runnable task, int delay, int ticks) {
        return Plugin.getInstance().runTimer(task, delay, ticks);
    }

    public static BukkitTask runTimerAsync(Runnable task, int ticks) {
        return runTimerAsync(task, 0, ticks);
    }

    public static BukkitTask runTimerAsync(Runnable task, int delay, int ticks) {
        return Plugin.getInstance().runTimerAsync(task, delay, ticks);
    }

    public <T> Future<T> callSync(Callable<T> callable) {
        return Plugin.getInstance().callSync(callable);
    }

    public <T> CompletableFuture<T> completeSync(Callable<T> callable) {
        return Plugin.getInstance().completeSync(callable);
    }

    private Tasks() {
    }
}

package me.jishuna.jishlib.util;

import me.jishuna.jishlib.JishlibPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Future;

public final class Tasks {
    public static BukkitTask run(Runnable task) {
        return Bukkit.getScheduler().runTask(JishlibPlugin.instance(), task);
    }

    public static BukkitTask runAsync(Runnable task) {
        return Bukkit.getScheduler().runTaskAsynchronously(JishlibPlugin.instance(), task);
    }

    public static BukkitTask runLater(Runnable task, long ticks) {
        return Bukkit.getScheduler().runTaskLater(JishlibPlugin.instance(), task, ticks);
    }

    public static BukkitTask runLaterAsync(Runnable task, long ticks) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(JishlibPlugin.instance(), task, ticks);
    }

    public static BukkitTask runTimer(Runnable task, long ticks) {
        return runTimer(task, 0, ticks);
    }

    public static BukkitTask runTimer(Runnable task, long delay, long ticks) {
        return Bukkit.getScheduler().runTaskTimer(JishlibPlugin.instance(), task, delay, ticks);
    }

    public static BukkitTask runTimerAsync(Runnable task, long ticks) {
        return runTimerAsync(task, 0, ticks);
    }

    public static BukkitTask runTimerAsync(Runnable task, long delay, long ticks) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(JishlibPlugin.instance(), task, delay, ticks);
    }

    public <T> Future<T> callSync(Callable<T> callable) {
        return Bukkit.getScheduler().callSyncMethod(JishlibPlugin.instance(), callable);
    }

    public <T> CompletableFuture<T> completeSync(Callable<T> callable) {
        return CompletableFuture.supplyAsync(() -> {
            Future<T> future = Bukkit.getScheduler().callSyncMethod(JishlibPlugin.instance(), callable);
            try {
                return future.get();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    private Tasks() {
    }
}

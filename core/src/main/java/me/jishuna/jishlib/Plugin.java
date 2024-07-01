package me.jishuna.jishlib;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Future;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import me.jishuna.jishlib.event.EventBus;
import me.jishuna.jishlib.nms.NMS;

public class Plugin extends JavaPlugin {
    private static Plugin INSTANCE;

    public static Plugin getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Instance is null!");
        }

        return INSTANCE;
    }

    public static NamespacedKey key(String value) {
        return new NamespacedKey(getInstance(), value);
    }

    final Set<Cleanable> cleanables = new HashSet<>();
    private EventBus eventBus;

    @Override
    public final void onEnable() {
        INSTANCE = this;
        Capabilities.init();
        Constants.init();

        onEnable(isReload());
    }

    protected void onEnable(boolean reload) {
    }

    @Override
    public final void onDisable() {
        this.cleanables.forEach(Cleanable::cleanup);
        this.cleanables.clear();

        INSTANCE = null;
        onDisable(isReload());
    }

    protected void onDisable(boolean reload) {
    }

    public BukkitTask run(Runnable task) {
        return Bukkit.getScheduler().runTask(this, task);
    }

    public BukkitTask runAsync(Runnable task) {
        return Bukkit.getScheduler().runTaskAsynchronously(this, task);
    }

    public BukkitTask runLater(Runnable task, int ticks) {
        return Bukkit.getScheduler().runTaskLater(this, task, ticks);
    }

    public BukkitTask runLaterAsync(Runnable task, int ticks) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(this, task, ticks);
    }

    public BukkitTask runTimer(Runnable task, int ticks) {
        return runTimer(task, 0, ticks);
    }

    public BukkitTask runTimer(Runnable task, int delay, int ticks) {
        return Bukkit.getScheduler().runTaskTimer(this, task, delay, ticks);
    }

    public BukkitTask runTimerAsync(Runnable task, int ticks) {
        return runTimerAsync(task, 0, ticks);
    }

    public BukkitTask runTimerAsync(Runnable task, int delay, int ticks) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(this, task, delay, ticks);
    }

    public <T> Future<T> callSync(Callable<T> callable) {
        return Bukkit.getScheduler().callSyncMethod(this, callable);
    }

    public <T> CompletableFuture<T> completeSync(Callable<T> callable) {
        return CompletableFuture.supplyAsync(() -> {
            Future<T> future = Bukkit.getScheduler().callSyncMethod(this, callable);
            try {
                return future.get();
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        });
    }

    public EventBus getEventBus() {
        if (this.eventBus == null) {
            this.eventBus = new EventBus(INSTANCE, EventPriority.NORMAL);
        }

        return this.eventBus;
    }

    public void registerCleanup(Cleanable feature) {
        this.cleanables.add(feature);
    }

    private boolean isReload() {
        if (!Capabilities.NMS) {
            return !Bukkit.getOnlinePlayers().isEmpty();
        }

        return NMS.get().getReloadCount() > 0;
    }
}

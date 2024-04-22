package me.jishuna.jishlib;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;
import me.jishuna.jishlib.event.EventBus;

public class SpigotPlugin extends JavaPlugin {
    private static SpigotPlugin INSTANCE;

    public static SpigotPlugin getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Instance is null!");
        }

        return INSTANCE;
    }

    public static Logger logger() {
        return getInstance().getLogger();
    }

    private EventBus eventBus;
    private Set<Feature> activeFeatures = new HashSet<>();

    @Override
    public void onEnable() {
        INSTANCE = this;
    }

    @Override
    public void onDisable() {
        this.activeFeatures.forEach(Feature::cleanup);
        this.activeFeatures.clear();

        INSTANCE = null;
    }

    public void run(Runnable task) {
        Bukkit.getScheduler().runTask(this, task);
    }

    public void runAsync(Runnable task) {
        Bukkit.getScheduler().runTaskAsynchronously(this, task);
    }

    public void runLater(Runnable task, int ticks) {
        Bukkit.getScheduler().runTaskLater(this, task, ticks);
    }

    public void runLaterAsync(Runnable task, int ticks) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(this, task, ticks);
    }

    public void runTimer(Runnable task, int ticks) {
        runTimer(task, 0, ticks);
    }

    public void runTimer(Runnable task, int delay, int ticks) {
        Bukkit.getScheduler().runTaskTimer(this, task, delay, ticks);
    }

    public void runTimerAsync(Runnable task, int ticks) {
        runTimerAsync(task, 0, ticks);
    }

    public void runTimerAsync(Runnable task, int delay, int ticks) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, task, delay, ticks);
    }

    public EventBus getEventBus() {
        if (this.eventBus == null) {
            this.eventBus = new EventBus(INSTANCE, EventPriority.NORMAL);
        }

        return this.eventBus;
    }

    public Set<Feature> getActiveFeatures() {
        return this.activeFeatures;
    }
}

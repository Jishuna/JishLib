package me.jishuna.jishlib;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;
import me.jishuna.jishlib.event.EventBus;
import me.jishuna.jishlib.util.Capabilities;

public class Plugin extends JavaPlugin {
    private static Plugin INSTANCE;

    public static Plugin getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Instance is null!");
        }

        return INSTANCE;
    }

    private Set<Feature> activeFeatures = new HashSet<>();
    private EventBus eventBus;

    @Override
    public void onEnable() {
        INSTANCE = this;
        Capabilities.init();
    }

    @Override
    public void onDisable() {
        this.activeFeatures.forEach(Feature::cleanup);
        this.activeFeatures.clear();

        if (this.eventBus != null) {
            this.eventBus.discard();
        }

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

    public void registerFeature(Feature feature) {
        this.activeFeatures.add(feature);
    }
}

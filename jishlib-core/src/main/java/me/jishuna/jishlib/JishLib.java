package me.jishuna.jishlib;

import com.google.common.base.Preconditions;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class JishLib {
    private static JishLib instance;

    private final Plugin plugin;

    private JishLib(Plugin plugin) {
        this.plugin = plugin;
    }

    public static void initialize(Plugin plugin) {
        Preconditions.checkArgument(instance == null, "JishLib already initialized!");
        instance = new JishLib(plugin);
    }

    public static Plugin getPlugin() {
        return getInstance().plugin;
    }

    public static Logger getLogger() {
        return getInstance().plugin.getLogger();
    }

    public static void cleanup() {
        instance = null;
    }

    public static boolean isInitialized() {
        return instance != null;
    }

    private static JishLib getInstance() {
        if (!isInitialized()) {
            throw new IllegalStateException("JishLib not initialized!");
        }

        return instance;
    }

    public static void run(Runnable task) {
        Bukkit.getScheduler().runTask(getPlugin(), task);
    }

    public static void runAsync(Runnable task) {
        Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), task);
    }

    public static void runLater(Runnable task, int ticks) {
        Bukkit.getScheduler().runTaskLater(getPlugin(), task, ticks);
    }

    public static void runLaterAsync(Runnable task, int ticks) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(getPlugin(), task, ticks);
    }
}

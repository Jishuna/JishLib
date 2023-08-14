package me.jishuna.jishlib;

import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;

import me.jishuna.jishlib.config.ConfigurationManager;

public class JishLib {
    private static JishLib instance;

    private final Plugin plugin;
    private final ConfigurationManager configManager;

    public JishLib(Plugin plugin) {
        this.plugin = plugin;
        this.configManager = new ConfigurationManager(plugin);
    }

    public static void initialize(Plugin plugin) {
        instance = new JishLib(plugin);
    }

    public static void cleanup() {
        instance = null;
    }

    public static JishLib getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Not itialized");
        }

        return instance;
    }

    public static Plugin getPluginInstance() {
        return getInstance().plugin;
    }

    public static Logger getLogger() {
        return getInstance().plugin.getLogger();
    }

    public static ConfigurationManager getConfigurationManaher() {
        return getInstance().configManager;
    }
}

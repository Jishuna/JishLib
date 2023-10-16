package me.jishuna.jishlib;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.plugin.Plugin;

import me.jishuna.jishlib.config.ConfigurationManager;
import me.jishuna.jishlib.inventory.CustomInventoryListener;
import me.jishuna.jishlib.inventory.CustomInventoryManager;

public class JishLib {
    private static JishLib instance;

    private final Plugin plugin;
    private final ConfigurationManager configManager;
    private final CustomInventoryManager inventoryManager = new CustomInventoryManager();
    private final ConversationFactory conversationFactory;

    public JishLib(Plugin plugin) {
        this.plugin = plugin;
        this.configManager = new ConfigurationManager(plugin);
        this.conversationFactory = new ConversationFactory(plugin);
    }

    public static void initialize(Plugin plugin) {
        instance = new JishLib(plugin);
    }

    public static void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new CustomInventoryListener(getInventoryManager()), getPluginInstance());
    }

    public static void initializeMessages(String fileName) {
        MessageHandler.initalize(getConfigurationManager(), new File(getPluginInstance().getDataFolder(), fileName), getPluginInstance().getResource(fileName));
    }

    public static void run(Runnable task) {
        Bukkit.getScheduler().runTask(getPluginInstance(), task);
    }

    public static void runAsync(Runnable task) {
        Bukkit.getScheduler().runTaskAsynchronously(getPluginInstance(), task);
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

    public static ConfigurationManager getConfigurationManager() {
        return getInstance().configManager;
    }

    public static CustomInventoryManager getInventoryManager() {
        return getInstance().inventoryManager;
    }

    public static ConversationFactory getConversationFactory() {
        return getInstance().conversationFactory;
    }
}

package me.jishuna.jishlib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import me.jishuna.jishlib.config.ConfigurationManager;
import me.jishuna.jishlib.conversation.ConversationManager;
import me.jishuna.jishlib.inventory.CustomInventoryListener;
import me.jishuna.jishlib.inventory.CustomInventoryManager;
import me.jishuna.jishlib.message.MessageParser;
import me.jishuna.jishlib.message.MessageReader;
import me.jishuna.jishlib.message.MessageWriter;
import me.jishuna.jishlib.message.Messages;

public class JishLib {
    private static JishLib instance;

    private final String messageFile;
    private final ConfigurationManager configManager;
    private final ConversationManager conversationManager;
    private final CustomInventoryManager inventoryManager;
    private final Plugin plugin;

    private JishLib(Plugin plugin, Collection<Component> components, String messageFile) {
        this.plugin = plugin;
        this.messageFile = messageFile;

        this.configManager = getConfigurationManager(components, plugin);
        this.inventoryManager = getInventoryManager(components, plugin);
        this.conversationManager = getConversationManager(components, plugin);
    }

    public static Builder build(Plugin plugin) {
        return new Builder(plugin);
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

    public static ConfigurationManager getConfigurationManager() {
        return getInstance().configManager;
    }

    public static CustomInventoryManager getInventoryManager() {
        return getInstance().inventoryManager;
    }

    public static ConversationManager getConversationManager() {
        return getInstance().conversationManager;
    }

    public static JishLib getInstance() {
        if (instance == null) {
            throw new IllegalStateException("JishLib not itialized!");
        }

        return instance;
    }

    public static void reloadMessages() {
        String fileName = getInstance().messageFile;
        if (fileName == null) {
            throw new IllegalStateException("Message file not set!");
        }

        File folder = getPlugin().getDataFolder();

        try {
            if (!folder.exists() && !folder.mkdirs()) {
                getLogger().severe("Failed to load messages: Failed to create message file");
                return;
            }
            MessageParser parser = MessageParser.empty();
            File target = new File(folder, fileName);

            if (target.exists()) {
                MessageReader external = new MessageReader(new FileInputStream(target));
                parser.merge(MessageParser.parse(external.readMessages()));
            } else if (!target.createNewFile()) {
                getLogger().severe("Failed to load messages: Failed to create message file");
                return;
            }

            MessageReader internal = new MessageReader(getPlugin().getResource(fileName));
            if (parser.merge(MessageParser.parse(internal.readMessages()))) {
                MessageWriter writer = new MessageWriter(new FileOutputStream(target));
                writer.writeMessages(parser.getRawMessages().values());
            }

            Messages.initialize(parser);
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Failed to load messages: {0}", e.getMessage());
            e.printStackTrace();
        }
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

    private ConfigurationManager getConfigurationManager(Collection<Component> components, Plugin plugin) {
        return components.contains(Component.CONFIGURATION) ? new ConfigurationManager(plugin) : null;
    }

    private CustomInventoryManager getInventoryManager(Collection<Component> components, Plugin plugin) {
        if (components.contains(Component.INVENTORY)) {
            CustomInventoryManager manager = new CustomInventoryManager();
            Bukkit.getPluginManager().registerEvents(new CustomInventoryListener(manager), plugin);
            return manager;
        }

        return null;
    }

    private ConversationManager getConversationManager(Collection<Component> components, Plugin plugin) {
        if (components.contains(Component.CONVERSATION)) {
            ConversationManager manager = new ConversationManager();
            Bukkit.getPluginManager().registerEvents(manager, plugin);
            return manager;
        }

        return null;
    }

    public static class Builder {
        private final Plugin plugin;
        private String messageFile = null;
        private final List<Component> components = new ArrayList<>();

        private Builder(Plugin plugin) {
            this.plugin = plugin;
        }

        public Builder withComponents(Component... components) {
            Collections.addAll(this.components, components);
            return this;
        }

        public Builder withMessageFile(String fileName) {
            this.messageFile = fileName;
            return this;
        }

        public void initialize() {
            instance = new JishLib(this.plugin, this.components, this.messageFile);
            reloadMessages();
        }
    }
}

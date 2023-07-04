package me.jishuna.jishlib;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

import me.jishuna.jishlib.config.ConfigReloadable;
import me.jishuna.jishlib.config.ConfigurationManager;
import me.jishuna.jishlib.config.annotation.ConfigEntry;

public class MessageHandler {
    private static MessageHandler instance;

    @ConfigEntry("")
    private final Map<String, List<String>> stringLists = new LinkedHashMap<>();
    @ConfigEntry("")
    private final Map<String, String> strings = new LinkedHashMap<>();

    private final ConfigReloadable<MessageHandler> reloadable;

    public MessageHandler(ConfigurationManager manager, File target) {
        this.reloadable = manager.createReloadable(target, this);
    }

    public void reload() {
        this.reloadable.load();
    }

    public String getString(String key) {
        return this.strings.getOrDefault(key, key);
    }

    public List<String> getStringList(String key) {
        return this.stringLists.getOrDefault(key, Collections.emptyList());
    }

    private void addString(String key, String value) {
        this.strings.put(key, StringUtils.miniMessageToLegacy(value));
    }

    private void addStringList(String key, List<String> value) {
        this.stringLists.put(key, value.stream().map(StringUtils::miniMessageToLegacy).toList());
    }

    public static MessageHandler getInstance() {
        return instance;
    }

    public static String get(String key) {
        return getInstance().getString(key);
    }

    public static String get(String key, Object... format) {
        return MessageFormat.format(getInstance().getString(key), format);
    }

    public static List<String> getList(String key) {
        return getInstance().getStringList(key);
    }

    public static List<String> getList(String key, Object... format) {
        List<String> list = getInstance().getStringList(key);
        return list.stream().map(string -> MessageFormat.format(string, format)).toList();
    }

    public static void initalize(ConfigurationManager manager, File target, InputStream internal) {
        if (instance != null) {
            return;
        }

        instance = new MessageHandler(manager, target);

        try (InputStreamReader reader = new InputStreamReader(internal)) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(reader);

            for (String key : config.getKeys(true)) {
                if (config.isString(key)) {
                    instance.addString(key, config.getString(key));
                } else if (config.isList(key)) {
                    instance.addStringList(key, config.getStringList(key));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        instance.reloadable.saveDefaults().load();
    }
}

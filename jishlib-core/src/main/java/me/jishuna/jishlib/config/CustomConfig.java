package me.jishuna.jishlib.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

public class CustomConfig extends YamlConfiguration {
    public CustomConfig() {
    }

    public CustomConfig(Map<?, ?> map) {
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (entry.getValue() instanceof Map) {
                createSection(entry.getKey().toString(), (Map<?, ?>) entry.getValue());
            } else {
                set(entry.getKey().toString(), entry.getValue());
            }
        }
    }

    public static CustomConfig loadConfiguration(@NotNull File file) {
        Validate.notNull(file, "File cannot be null");

        CustomConfig config = new CustomConfig();

        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot load " + file, ex);
        }

        return config;
    }

    @Override
    public Object get(String path, Object def) {
        Object value = super.get(path, def);
        if (value instanceof Map<?, ?> map) {
            value = new CustomConfig(map);
        }

        if (value instanceof List<?> list) {
            List<Object> newList = new ArrayList<>(list.size());

            for (Object entry : list) {
                if (entry instanceof Map<?, ?> map) {
                    newList.add(new CustomConfig(map));
                } else {
                    newList.add(entry);
                }
            }

            value = newList;
        }

        if (value != null) {
            System.out.println("Config get: [Path: %s, Type: %s, Value: %s]".formatted(path, value.getClass().getName(), value.toString()));
        }

        return value;
    }

    @Override
    public boolean contains(@NotNull String path, boolean ignoreDefault) {
        return ((ignoreDefault) ? getRaw(path, null) : getRaw(path, getDefault(path))) != null;
    }

    @Override
    public boolean isSet(@NotNull String path) {
        Configuration root = getRoot();
        if (root == null) {
            return false;
        }
        if (root.options().copyDefaults()) {
            return contains(path);
        }
        return getRaw(path, null) != null;
    }

    private Object getRaw(String path, Object def) {
        return super.get(path, def);
    }
}

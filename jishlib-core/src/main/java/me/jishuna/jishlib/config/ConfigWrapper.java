package me.jishuna.jishlib.config;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.configuration.ConfigurationSection;

public class ConfigWrapper {
    private final Map<String, Object> values;

    public ConfigWrapper() {
        this.values = new LinkedHashMap<>();
    }

    public ConfigWrapper(Map<String, Object> values) {
        this.values = values;
    }

    public void set(String path, Object value) {
        this.values.put(path, value);
    }

    public Map<String, Object> getSection(String key) {
        Object value = this.values.get(key);
        if (value instanceof ConfigurationSection section) {
            return section.getValues(true);
        }
        return new LinkedHashMap<>();
    }

    public Map<String, Object> getOrCreateSection(String key) {
        return getSection(key);
    }

    public boolean has(String key) {
        return this.values.containsKey(key);
    }

    public Map<String, Object> getValues() {
        return Collections.unmodifiableMap(this.values);
    }
}

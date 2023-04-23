package me.jishuna.jishlib.config.adapter;

import org.bukkit.configuration.ConfigurationSection;

import me.jishuna.jishlib.StringUtils;

public class StringTypeAdapter implements TypeAdapter<String> {
    
    public String read(ConfigurationSection config, String path) {
        String value = config.getString(path);
        if (value != null) {
            value = StringUtils.parseToLegacy(value);
        }

        return value;
    }
    
    public void write(ConfigurationSection config, String path, Object value) {
        config.set(path, value);
    }
}

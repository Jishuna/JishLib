package me.jishuna.jishlib.config.adapter;

import org.bukkit.configuration.ConfigurationSection;

public class StringTypeAdapter implements TypeAdapter<String> {
    
    public String read(ConfigurationSection config, String path) {
        return config.getString(path);
    }
    
    public void write(ConfigurationSection config, String path, String value) {
        config.set(path, value);
    }
}

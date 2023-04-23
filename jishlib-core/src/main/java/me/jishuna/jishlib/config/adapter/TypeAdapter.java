package me.jishuna.jishlib.config.adapter;

import org.bukkit.configuration.ConfigurationSection;

public interface TypeAdapter<T> {

    public T read(ConfigurationSection config, String path);
    
    public void write(ConfigurationSection config, String path, Object value);
}

package me.jishuna.jishlib.config.adapter;

import org.bukkit.configuration.ConfigurationSection;

public interface TypeAdapter<T> {

    public T read(Object value);

    public void write(ConfigurationSection config, String path, T value, boolean replace);

    @SuppressWarnings("unchecked")
    public default void writeObject(ConfigurationSection config, String path, Object value, boolean replace) {
        write(config, path, (T) value, replace);
    }
}

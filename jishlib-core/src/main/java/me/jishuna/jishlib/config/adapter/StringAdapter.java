package me.jishuna.jishlib.config.adapter;

import org.bukkit.configuration.ConfigurationSection;

public interface StringAdapter<T> extends TypeAdapter<T> {
    public String toString(T value);

    public T fromString(String value);

    @Override
    default T read(ConfigurationSection config, String path) {
        String value = config.getString(path);
        return value == null ? null : fromString(value);
    }

    @Override
    default void write(ConfigurationSection config, String path, T value, boolean replace) {
        if ((value == null) || (config.isSet(path) && !replace)) {
            return;
        }

        config.set(path, toString(value));
    }
}

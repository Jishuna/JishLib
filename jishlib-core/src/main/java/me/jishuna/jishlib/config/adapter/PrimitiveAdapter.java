package me.jishuna.jishlib.config.adapter;

import java.util.function.Function;

import org.bukkit.configuration.ConfigurationSection;

public class PrimitiveAdapter<T> implements TypeAdapter<T> {

    private final Function<String, T> reader;

    public PrimitiveAdapter(Function<String, T> reader) {
        this.reader = reader;
    }

    @Override
    public T read(ConfigurationSection config, String path) {
        String value = config.getString(path);
        return this.reader.apply(value);
    }

    @Override
    public void write(ConfigurationSection config, String path, T value) {
        config.set(path, value);
    }
}

package me.jishuna.jishlib.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import me.jishuna.jishlib.config.adapter.MaterialTypeAdapter;
import me.jishuna.jishlib.config.adapter.PrimitiveAdapter;
import me.jishuna.jishlib.config.adapter.StringTypeAdapter;
import me.jishuna.jishlib.config.adapter.TypeAdapter;

public class ConfigurationManager {

    private final Map<Class<?>, TypeAdapter<?>> adapters = new HashMap<>();
    private final Plugin plugin;
    private final Logger logger;

    public ConfigurationManager(Plugin plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();

        registerTypeAdapter(long.class, new PrimitiveAdapter<>(Long::parseLong));
        registerTypeAdapter(Long.class, new PrimitiveAdapter<>(Long::parseLong));

        registerTypeAdapter(int.class, new PrimitiveAdapter<>(Integer::parseInt));
        registerTypeAdapter(Integer.class, new PrimitiveAdapter<>(Integer::parseInt));

        registerTypeAdapter(double.class, new PrimitiveAdapter<>(Double::parseDouble));
        registerTypeAdapter(Double.class, new PrimitiveAdapter<>(Double::parseDouble));

        registerTypeAdapter(float.class, new PrimitiveAdapter<>(Float::parseFloat));
        registerTypeAdapter(Float.class, new PrimitiveAdapter<>(Float::parseFloat));

        registerTypeAdapter(boolean.class, new PrimitiveAdapter<>(Boolean::parseBoolean));
        registerTypeAdapter(Boolean.class, new PrimitiveAdapter<>(Boolean::parseBoolean));

        registerTypeAdapter(String.class, new StringTypeAdapter());
        registerTypeAdapter(Material.class, new MaterialTypeAdapter());

    }

    public <T> void registerTypeAdapter(Class<T> clazz, TypeAdapter<T> adapter) {
        this.adapters.put(clazz, adapter);
    }

    public <T> ReloadableObject<T> createReloadable(File file, T target) {
        return new ReloadableObject<>(this, file, target);
    }

    public <T> ReloadableClass<T> createStaticReloadable(File file, Class<T> target) {
        return new ReloadableClass<>(this, file, target);
    }

    public TypeAdapter<?> getAdapter(Class<?> type) {
        return this.adapters.get(type);
    }
}

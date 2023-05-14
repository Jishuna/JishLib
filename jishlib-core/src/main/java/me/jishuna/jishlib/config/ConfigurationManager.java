package me.jishuna.jishlib.config;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import me.jishuna.jishlib.collections.WeightedRandom;
import me.jishuna.jishlib.config.adapter.CollectionTypeAdapter;
import me.jishuna.jishlib.config.adapter.EnumAdapter;
import me.jishuna.jishlib.config.adapter.MaterialTypeAdapter;
import me.jishuna.jishlib.config.adapter.PrimitiveAdapter;
import me.jishuna.jishlib.config.adapter.StringAdapter;
import me.jishuna.jishlib.config.adapter.StringTypeAdapter;
import me.jishuna.jishlib.config.adapter.TypeAdapter;
import me.jishuna.jishlib.config.adapter.WeightedRandomAdapter;

public class ConfigurationManager {

    private final Map<ConfigType<?>, TypeAdapter<?>> adapters = new HashMap<>();
    private final Logger logger;

    public ConfigurationManager(Plugin plugin) {
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
        this.adapters.put(new ConfigType<>(clazz), adapter);
    }

    public <T> ReloadableObject<T> createReloadable(File file, T target) {
        return new ReloadableObject<>(this, file, target);
    }

    public <T> ReloadableClass<T> createStaticReloadable(File file, Class<T> target) {
        return new ReloadableClass<>(this, file, target);
    }

    public <T> StringAdapter<T> getStringAdapter(ConfigType<T> type) {
        TypeAdapter<T> adapter = getAdapter(type);

        if (adapter instanceof StringAdapter<T> stringAdapter) {
            return stringAdapter;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> getAdapter(ConfigType<T> type) {
        TypeAdapter<T> adapter = (TypeAdapter<T>) this.adapters.get(type);

        if (adapter != null) {
            return adapter;
        }

        adapter = (TypeAdapter<T>) createAdapter(type);
        this.adapters.put(type, adapter);
        return adapter;
    }

    private TypeAdapter<?> createAdapter(ConfigType<?> type) {
        if (Enum.class.isAssignableFrom(type.getType())) {
            return new EnumAdapter<>(type.getType());
        }
        if (Collection.class.isAssignableFrom(type.getType())) {
            return new CollectionTypeAdapter<>(this, type);
        }

        if (WeightedRandom.class.isAssignableFrom(type.getType())) {
            System.out.println(type);
            return new WeightedRandomAdapter<>(this, type);
        }
        return null;
    }

    public Logger getLogger() {
        return this.logger;
    }
}

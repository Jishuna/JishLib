package me.jishuna.jishlib.config;

import com.google.common.base.Preconditions;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import me.jishuna.jishlib.JishLib;
import me.jishuna.jishlib.config.adapter.CollectionAdapter;
import me.jishuna.jishlib.config.adapter.ConfigMappableAdapter;
import me.jishuna.jishlib.config.adapter.EnumAdapter;
import me.jishuna.jishlib.config.adapter.MapAdapter;
import me.jishuna.jishlib.config.adapter.MaterialAdapter;
import me.jishuna.jishlib.config.adapter.NamespacedKeyAdapter;
import me.jishuna.jishlib.config.adapter.PrimitiveAdapter;
import me.jishuna.jishlib.config.adapter.StringAdapter;
import me.jishuna.jishlib.config.adapter.StringTypeAdapter;
import me.jishuna.jishlib.config.adapter.TypeAdapter;
import me.jishuna.jishlib.config.adapter.WeightedRandomAdapter;
import me.jishuna.jishlib.config.annotation.ConfigMappable;
import me.jishuna.jishlib.config.reloadable.ReloadableClass;
import me.jishuna.jishlib.config.reloadable.ReloadableObject;
import me.jishuna.jishlib.datastructure.WeightedRandom;

public final class ConfigApi {
    public static final MaterialAdapter MATERIAL_ADAPTER = new MaterialAdapter();
    public static final NamespacedKeyAdapter NAMESPACE_ADAPTER = new NamespacedKeyAdapter();

    private static ConfigurationManager manager;

    public static void initialize() {
        Preconditions.checkArgument(manager == null, "ConfigApi already initialized!");
        Preconditions.checkArgument(JishLib.isInitialized(), "JishLib must be initialized first!");

        manager = new ConfigurationManager();
    }

    public static <T> void registerTypeAdapter(Class<T> clazz, TypeAdapter<T> adapter) {
        getInstance().adapters.put(new ConfigType<>(clazz), adapter);
    }

    public static <T> ReloadableObject<T> createReloadable(File file, T target) {
        return new ReloadableObject<>(file, target);
    }

    public static <T> ReloadableClass<T> createStaticReloadable(File file, Class<T> target) {
        return new ReloadableClass<>(file, target);
    }

    public static <T> StringAdapter<T> getStringAdapter(ConfigType<T> type) {
        TypeAdapter<T> adapter = getAdapter(type);

        if (adapter instanceof StringAdapter<T> stringAdapter) {
            return stringAdapter;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> TypeAdapter<T> getAdapter(ConfigType<T> type) {
        TypeAdapter<T> adapter = (TypeAdapter<T>) getInstance().adapters.get(type);

        if (adapter != null) {
            return adapter;
        }

        adapter = (TypeAdapter<T>) createAdapter(type);
        getInstance().adapters.put(type, adapter);
        return adapter;
    }

    private static TypeAdapter<?> createAdapter(ConfigType<?> type) {
        if (Enum.class.isAssignableFrom(type.getType())) {
            return new EnumAdapter<>(type.getType());
        }
        if (Collection.class.isAssignableFrom(type.getType())) {
            return new CollectionAdapter<>(type);
        }

        if (Map.class.isAssignableFrom(type.getType())) {
            return new MapAdapter<>(type);
        }

        if (WeightedRandom.class.isAssignableFrom(type.getType())) {
            return new WeightedRandomAdapter<>(type);
        }

        if (type.getType().isAnnotationPresent(ConfigMappable.class)) {
            return new ConfigMappableAdapter<>(type);
        }
        return null;
    }

    private static ConfigurationManager getInstance() {
        if (manager == null) {
            throw new IllegalStateException("ConfigApi not initialized!");
        }
        return manager;
    }

    private ConfigApi() {
    }

    static final class ConfigurationManager {
        private final Map<ConfigType<?>, TypeAdapter<?>> adapters = new HashMap<>();

        public ConfigurationManager() {
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
            registerTypeAdapter(NamespacedKey.class, NAMESPACE_ADAPTER);
            registerTypeAdapter(Material.class, MATERIAL_ADAPTER);
        }

        public <T> void registerTypeAdapter(Class<T> clazz, TypeAdapter<T> adapter) {
            this.adapters.put(new ConfigType<>(clazz), adapter);
        }
    }
}

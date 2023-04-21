package me.jishuna.jishlib.config;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import me.jishuna.jishlib.config.adapter.MaterialTypeAdapter;
import me.jishuna.jishlib.config.adapter.PrimitiveAdapter;
import me.jishuna.jishlib.config.adapter.StringTypeAdapter;
import me.jishuna.jishlib.config.adapter.TypeAdapter;

public class ConfigurationParser {

    private final Map<Class<?>, TypeAdapter<?>> adapters = new HashMap<>();
    private final Logger logger;

    public ConfigurationParser(Logger logger) {
        this.logger = logger;

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

    public void parseStatic(YamlConfiguration config, Class<?> clazz) {
        List<Field> configFields = getStaticConfigFields(clazz);

        for (Field field : configFields) {
            String path = field.getAnnotation(ConfigEntry.class).value();
            if (path.isEmpty()) {
                path = field.getName().toLowerCase();
            }

            if (!config.isSet(path)) {
                logger.log(Level.WARNING, "No configuration entry found for {0}", path);
                continue;
            }

            Class<?> type = field.getType();
            TypeAdapter<?> adapter = this.adapters.get(type);
            if (adapter == null) {
                logger.log(Level.WARNING, "No configuration adapter found for {0}", type.getName());
                continue;
            }

            Object readValue = adapter.read(config, path);
            if (readValue == null) {
                logger.log(Level.WARNING, "Failed to read value for {0} of type {1}",
                        new Object[] { path, type.getName() });
                continue;
            }

            try {
                field.set(null, readValue);
            } catch (ReflectiveOperationException ex) {
                logger.log(Level.WARNING, "Failed to read value for {0} of type {1}",
                        new Object[] { path, type.getName() });
                ex.printStackTrace();
            }
        }

    }

    private List<Field> getStaticConfigFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();

        for (Field field : clazz.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers()) || !field.isAnnotationPresent(ConfigEntry.class)) {
                continue;
            }

            fields.add(field);
        }
        return fields;
    }
}

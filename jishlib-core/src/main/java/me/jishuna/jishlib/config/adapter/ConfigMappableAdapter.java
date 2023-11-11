package me.jishuna.jishlib.config.adapter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.ConfigurationSection;
import me.jishuna.jishlib.config.ConfigField;
import me.jishuna.jishlib.config.ConfigType;
import me.jishuna.jishlib.config.ConfigurationManager;
import me.jishuna.jishlib.config.ReflectionHelper;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.jishlib.config.annotation.PostLoad;

public class ConfigMappableAdapter<T> implements TypeAdapter<T> {
    private final ConfigurationManager manager;
    private final List<ConfigField> fields = new ArrayList<>();
    private final ConfigType<T> type;
    private final Method postLoadMethod;

    @SuppressWarnings("unchecked")
    public ConfigMappableAdapter(ConfigurationManager manager, ConfigType<?> type) {
        this.manager = manager;
        this.type = (ConfigType<T>) type;
        this.postLoadMethod = findPostLoadMethod(this.type.getType());

        cacheFields(this.type.getType());
    }

    @Override
    public T read(ConfigurationSection config, String path) {
        try {
            T object = this.type.getType().getDeclaredConstructor().newInstance();

            for (ConfigField field : this.fields) {
                String fullPath = path + "." + field.getPath();

                if (!config.isSet(fullPath)) {
                    // logger.log(Level.WARNING, "No configuration entry found for {0}", path);
                    continue;
                }

                ConfigType<?> type = ConfigType.get(field.getField());
                TypeAdapter<?> adapter = this.manager.getAdapter(type);
                if (adapter == null) {
                    // logger.log(Level.WARNING, "No configuration adapter found for {0}",
                    // type.getClass());
                    continue;
                }

                Object readValue = adapter.read(config, fullPath);
                if (readValue == null) {
                    // logger.log(Level.WARNING, "Failed to read value for {0} of type {1}",
                    // new Object[] { path, type.getType() });
                    continue;
                }

                ReflectionHelper.setField(field, readValue, object);
            }

            if (this.postLoadMethod != null) {
                this.postLoad(this.postLoadMethod, object);
            }

            return object;
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public void write(ConfigurationSection config, String path, T value, boolean replace) {
        for (ConfigField field : this.fields) {
            String fullPath = path + "." + field.getPath();

            if (!fullPath.isEmpty()) {
                // config.setComments(fullPath, field.getComments());
            }

            ConfigType<?> type = ConfigType.get(field.getField());
            TypeAdapter<?> adapter = this.manager.getAdapter(type);
            if (adapter == null) {
                // logger.log(Level.WARNING, "No configuration adapter found for {0}",
                // type.getType());
                continue;
            }

            Object writeValue = ReflectionHelper.getField(field, value);
            if (writeValue == null) {
                // logger.log(Level.WARNING, "null");
                continue;
            }

            adapter.writeObject(config, fullPath, writeValue, replace);
            // config.setComments(fullPath, field.getComments());
        }
    }

    private void postLoad(Method postLoadMethod, T object) {
        try {
            boolean access = postLoadMethod.canAccess(object);
            if (!access) {
                postLoadMethod.trySetAccessible();
            }

            postLoadMethod.invoke(object);

            if (!access) {
                postLoadMethod.setAccessible(false);
            }
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
        }
    }

    private void cacheFields(Class<? super T> clazz) {
        int index = 0;
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(ConfigEntry.class)) {
                continue;
            }

            this.fields.add(index++, new ConfigField(field));
        }

        Class<? super T> superClass = clazz.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            cacheFields(superClass);
        }
    }

    private Method findPostLoadMethod(Class<? super T> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PostLoad.class)) {
                return method;
            }
        }

        Class<? super T> superClass = clazz.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            return findPostLoadMethod(superClass);
        }

        return null;
    }
}

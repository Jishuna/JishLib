package me.jishuna.jishlib.config.adapter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.configuration.ConfigurationSection;
import me.jishuna.jishlib.JishLib;
import me.jishuna.jishlib.config.ConfigAPI;
import me.jishuna.jishlib.config.ConfigField;
import me.jishuna.jishlib.config.ConfigType;
import me.jishuna.jishlib.config.ReflectionHelper;
import me.jishuna.jishlib.config.CustomConfig;
import me.jishuna.jishlib.config.annotation.ConfigEntry;

public class NativeAdapter<T> implements TypeAdapter<ConfigurationSection, T> {
    private final Class<T> clazz;
    private final List<ConfigField> fields = new ArrayList<>();

    public NativeAdapter(Class<T> clazz) {
        this.clazz = clazz;

        cacheFields(clazz);
    }

    @Override
    public Class<ConfigurationSection> getSavedType() {
        return ConfigurationSection.class;
    }

    @Override
    public Class<T> getRuntimeType() {
        return this.clazz;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T read(ConfigurationSection value) {
        T object;
        try {
            Constructor<T> constructor = this.clazz.getDeclaredConstructor();
            object = constructor.newInstance();
        } catch (ReflectiveOperationException e) {
            JishLib.getLogger().log(Level.WARNING, "Failed to create instance");
            return null;
        }

        for (ConfigField field : this.fields) {
            String path = field.getPath();

            if (!value.isSet(path)) {
                JishLib.getLogger().log(Level.WARNING, "No configuration entry found for {0}", path);
                continue;
            }

            ConfigType<?> type = ConfigType.get(field.getField());
            TypeAdapter<Object, Object> adapter = (TypeAdapter<Object, Object>) ConfigAPI.getAdapter(type);
            if (adapter == null) {
                JishLib.getLogger().log(Level.WARNING, "No configuration adapter found for {0}", type.getClass());
                continue;
            }

            Class<Object> saved = adapter.getSavedType();
            Object savedValue = getSavedValue(value, path, saved);
            if (!saved.isInstance(savedValue)) {
                JishLib.getLogger().log(Level.WARNING, "Wrong saved type for {0}, {1} != {2}", new Object[] { path, savedValue.getClass(), saved });
                continue;
            }

            Object readValue = adapter.read(saved.cast(savedValue));

            if (readValue == null) {
                JishLib.getLogger().log(Level.WARNING, "Failed to read value for {0} of type {1}", new Object[] { path, type.getType() });
                continue;
            }

            try {
                ReflectionHelper.setField(field, readValue, object);
            } catch (ReflectiveOperationException ex) {
                JishLib.getLogger().log(Level.WARNING, "Failed to read value for {0} of type {1}", new Object[] { path, type.getType() });
                ex.printStackTrace();
            }
        }

        return object;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ConfigurationSection write(T value, ConfigurationSection section, boolean replace) {
        if (section == null) {
            section = new CustomConfig();
        }

        for (ConfigField field : this.fields) {
            String path = field.getPath();

            ConfigType<?> type = ConfigType.get(field.getField());
            TypeAdapter<Object, Object> adapter = (TypeAdapter<Object, Object>) ConfigAPI.getAdapter(type);
            if (adapter == null) {
                JishLib.getLogger().log(Level.WARNING, "No configuration adapter found for {0}", type.getType());
                continue;
            }

            Object writeValue = ReflectionHelper.getField(field, value);
            if (writeValue == null) {
                JishLib.getLogger().log(Level.WARNING, "null");
                continue;
            }

            Class<Object> saved = adapter.getSavedType();
            Class<Object> runtime = adapter.getRuntimeType();
            if (!runtime.isInstance(writeValue)) {
                JishLib.getLogger().log(Level.WARNING, "Wrong runtime type for {0}, {1} != {2}", new Object[] { path, writeValue.getClass(), runtime });
                continue;
            }

            Object existing = getSavedValue(section, path, saved);
            if (existing != null && !saved.isInstance(existing)) {
                JishLib.getLogger().log(Level.WARNING, "Wrong saved type for {0}, {1} != {2}", new Object[] { path, existing.getClass(), saved });
                existing = null;
            }

            Object val = adapter.write(runtime.cast(writeValue), saved.cast(existing), replace);
            if (val != null) {
                section.set(path, val);
            }
        }
        return section;
    }

    private Object getSavedValue(ConfigurationSection value, String path, Class<Object> expectedType) {
        return value.get(path);
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
}

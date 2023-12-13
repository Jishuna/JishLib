package me.jishuna.jishlib.config.adapter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import me.jishuna.jishlib.JishLib;
import me.jishuna.jishlib.config.ConfigApi;
import me.jishuna.jishlib.config.ConfigField;
import me.jishuna.jishlib.config.ConfigType;
import me.jishuna.jishlib.config.ReflectionHelper;
import me.jishuna.jishlib.config.annotation.ConfigEntry;

public class NativeAdapter<T> implements TypeAdapter<Map<String, Object>, T> {
    private final Class<T> clazz;
    private final List<ConfigField> fields = new ArrayList<>();

    public NativeAdapter(Class<T> clazz) {
        this.clazz = clazz;

        cacheFields(clazz);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<Map<String, Object>> getSavedType() {
        return (Class<Map<String, Object>>) (Object) Map.class;
    }

    @Override
    public Class<T> getRuntimeType() {
        return this.clazz;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T read(Map<String, Object> value) {
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

            if (!value.containsKey(path)) {
                JishLib.getLogger().log(Level.WARNING, "No configuration entry found for {0}", path);
                continue;
            }

            ConfigType<?> type = ConfigType.get(field.getField());
            TypeAdapter<Object, Object> adapter = (TypeAdapter<Object, Object>) ConfigApi.getAdapter(type);
            if (adapter == null) {
                JishLib.getLogger().log(Level.WARNING, "No configuration adapter found for {0}", type.getClass());
                continue;
            }

            Object savedValue = value.get(path);
            Class<Object> saved = adapter.getSavedType();
            if (!saved.isInstance(savedValue)) {
                JishLib.getLogger().log(Level.WARNING, "Wrong saved type {0} != {1}", new Object[] { savedValue.getClass(), saved });
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
    public Map<String, Object> write(T value, Map<String, Object> map, boolean replace) {
        if (map == null) {
            map = new LinkedHashMap<>();
        }

        for (ConfigField field : this.fields) {
            String path = field.getPath();

            ConfigType<?> type = ConfigType.get(field.getField());
            TypeAdapter<Object, Object> adapter = (TypeAdapter<Object, Object>) ConfigApi.getAdapter(type);
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
                JishLib.getLogger().log(Level.WARNING, "Wrong runtime type {0} != {1}", new Object[] { writeValue.getClass(), runtime });
                continue;
            }

            Object existing = map.get(path);
            if (existing != null && !saved.isInstance(existing)) {
                JishLib.getLogger().log(Level.WARNING, "Wrong saved type {0} != {1}", new Object[] { existing.getClass(), saved });
                existing = null;
            }

            Object val = adapter.write(runtime.cast(writeValue), saved.cast(existing), replace);
            if (val != null) {
                map.put(path, val);
            }
        }
        return map;
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

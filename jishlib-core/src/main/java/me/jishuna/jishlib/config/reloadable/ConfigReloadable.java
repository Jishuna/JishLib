package me.jishuna.jishlib.config.reloadable;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import me.jishuna.jishlib.JishLib;
import me.jishuna.jishlib.config.ConfigAPI;
import me.jishuna.jishlib.config.ConfigField;
import me.jishuna.jishlib.config.ConfigType;
import me.jishuna.jishlib.config.CustomConfig;
import me.jishuna.jishlib.config.adapter.TypeAdapter;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.jishlib.config.annotation.PostLoad;

public abstract class ConfigReloadable<T> {
    protected final File file;
    protected final List<ConfigField> fields = new ArrayList<>();
    private final Method postLoadMethod;

    protected ConfigReloadable(File file, Class<T> clazz) {
        this.file = file;
        this.postLoadMethod = findPostLoadMethod(clazz);

        cacheFields(clazz);
    }

    public ConfigReloadable<T> load() {
        load(true);

        return this;
    }

    @SuppressWarnings("unchecked")
    public ConfigReloadable<T> load(boolean includeStatic) {
        if (!prepareFile()) {
            // File error
            return this;
        }

        CustomConfig config = CustomConfig.loadConfiguration(this.file);

        for (ConfigField field : this.fields) {
            if (field.isStatic() && !includeStatic) {
                continue;
            }

            String path = field.getPath();

            ConfigType<?> type = ConfigType.get(field.getField());
            TypeAdapter<Object, Object> adapter = (TypeAdapter<Object, Object>) ConfigAPI.getAdapter(type);
            if (adapter == null) {
                JishLib.getLogger().log(Level.WARNING, "No configuration adapter found for {0}", type.getClass());
                continue;
            }

            Class<Object> saved = adapter.getSavedType();
            Object savedValue = config.get(path);
            if (savedValue == null) {
                JishLib.getLogger().log(Level.WARNING, "No configuration entry found for {0}", path);
                continue;
            }

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
                setField(field, readValue);
            } catch (ReflectiveOperationException ex) {
                JishLib.getLogger().log(Level.WARNING, "Failed to read value for {0} of type {1}", new Object[] { path, type.getType() });
                ex.printStackTrace();
            }
        }

        if (this.postLoadMethod != null) {
            this.postLoad(this.postLoadMethod);
        }
        return this;
    }

    public ConfigReloadable<T> saveDefaults() {
        save(false);

        return this;
    }

    public ConfigReloadable<T> save() {
        save(true);

        return this;
    }

    @SuppressWarnings("unchecked")
    public ConfigReloadable<T> save(boolean replace) {
        if (!prepareFile()) {
            // File error
            return this;
        }

        CustomConfig config = CustomConfig.loadConfiguration(this.file);

        for (ConfigField field : this.fields) {
            String path = field.getPath();

            if (!path.isEmpty()) {
                config.setComments(path, field.getComments());
            }

            ConfigType<?> type = ConfigType.get(field.getField());
            TypeAdapter<Object, Object> adapter = (TypeAdapter<Object, Object>) ConfigAPI.getAdapter(type);
            if (adapter == null) {
                JishLib.getLogger().log(Level.WARNING, "No configuration adapter found for {0}", type.getType());
                continue;
            }

            Object writeValue = getField(field);
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

            Object existing = config.get(path);
            if (existing != null && !saved.isInstance(existing)) {
                JishLib.getLogger().log(Level.WARNING, "Wrong saved type for {0}, {1} != {2}", new Object[] { path, existing.getClass(), saved });
                existing = null;
            }

            Object value = adapter.write(runtime.cast(writeValue), saved.cast(existing), replace);
            if (value != null) {
                config.set(path, value);
                config.setComments(path, field.getComments());
            }
        }

        try {
            config.save(this.file);
        } catch (IOException ex) {
            JishLib.getLogger().log(Level.WARNING, "Failed to save configuration file {0}", this.file.getName());
            ex.printStackTrace();
        }
        return this;
    }

    protected abstract void setField(ConfigField field, Object value) throws ReflectiveOperationException;

    protected abstract Object getField(ConfigField field);

    protected abstract void postLoad(Method postLoadMethod);

    private boolean prepareFile() {
        try {
            if (!this.file.getParentFile().exists() && !this.file.getParentFile().mkdirs()) {
                JishLib.getLogger().log(Level.WARNING, "Error creating file {0}", this.file.getName());
                return false;
            }

            if (!this.file.exists()) {
                return this.file.createNewFile();
            }

            return true;
        } catch (IOException ex) {
            JishLib.getLogger().log(Level.WARNING, "Error creating file {0}", this.file.getName());
            return false;
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

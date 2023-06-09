package me.jishuna.jishlib.config;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.YamlConfiguration;

import me.jishuna.jishlib.config.adapter.TypeAdapter;
import me.jishuna.jishlib.config.annotation.ConfigEntry;

public abstract class ConfigReloadable<T> {
    protected final ConfigurationManager manager;
    protected final File file;
    protected final List<ConfigField> fields = new ArrayList<>();
    protected final Logger logger;

    protected ConfigReloadable(ConfigurationManager manager, File file, Class<T> clazz) {
        this.manager = manager;
        this.file = file;
        this.logger = manager.getLogger();

        cacheFields(clazz);
        Collections.reverse(this.fields);
    }

    public ConfigReloadable<T> load() {
        load(true);

        return this;
    }

    public ConfigReloadable<T> load(boolean includeStatic) {
        if (!prepareFile()) {
            // File error
            return this;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (ConfigField field : this.fields) {
            if (field.isStatic() && !includeStatic) {
                continue;
            }

            String path = field.getPath();

            if (!config.isSet(path)) {
                logger.log(Level.WARNING, "No configuration entry found for {0}", path);
                continue;
            }

            ConfigType<?> type = ConfigType.get(field.getField());
            TypeAdapter<?> adapter = this.manager.getAdapter(type);
            if (adapter == null) {
                logger.log(Level.WARNING, "No configuration adapter found for {0}", type.getClass());
                continue;
            }

            Object readValue = adapter.read(config, path);
            if (readValue == null) {
                logger.log(Level.WARNING, "Failed to read value for {0} of type {1}",
                        new Object[] { path, type.getType() });
                continue;
            }

            try {
                setField(field, readValue);
            } catch (ReflectiveOperationException ex) {
                logger.log(Level.WARNING, "Failed to read value for {0} of type {1}",
                        new Object[] { path, type.getType() });
                ex.printStackTrace();
            }
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

    public ConfigReloadable<T> save(boolean replace) {
        if (!prepareFile()) {
            // File error
            return this;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        for (ConfigField field : this.fields) {
            String path = field.getPath();

            if (!replace && !path.isEmpty() && config.isSet(path)) {
                config.setComments(path, field.getComments());
                continue;
            }

            ConfigType<?> type = ConfigType.get(field.getField());
            TypeAdapter<?> adapter = this.manager.getAdapter(type);
            if (adapter == null) {
                logger.log(Level.WARNING, "No configuration adapter found for {0}", type.getType());
                continue;
            }

            Object writeValue = getField(field);
            if (writeValue == null) {
                logger.log(Level.WARNING, "null");
                continue;
            }

            adapter.write(config, path, writeValue, replace);
            config.setComments(path, field.getComments());
        }

        try {
            config.save(file);
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Failed to save configuration file {0}", file.getName());
            ex.printStackTrace();
        }
        return this;
    }

    protected abstract void setField(ConfigField field, Object value) throws ReflectiveOperationException;

    protected abstract Object getField(ConfigField field);

    private boolean prepareFile() {
        try {
            if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                logger.log(Level.WARNING, "Error creating file {0}", file.getName());
                return false;
            }

            if (!file.exists()) {
                return file.createNewFile();
            }

            return true;
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Error creating file {0}", file.getName());
            return false;
        }
    }

    private void cacheFields(Class<? super T> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(ConfigEntry.class)) {
                continue;
            }

            this.fields.add(new ConfigField(field));
        }

        Class<? super T> superClass = clazz.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            cacheFields(superClass);
        }
    }
}

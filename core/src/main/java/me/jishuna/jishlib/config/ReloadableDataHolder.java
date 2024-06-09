package me.jishuna.jishlib.config;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.file.YamlConfiguration;
import me.jishuna.jishlib.Logger;
import me.jishuna.jishlib.config.annotation.Path;
import me.jishuna.jishlib.data.DataType;
import me.jishuna.jishlib.data.adapter.TypeAdapter;
import me.jishuna.jishlib.data.adapter.TypeAdapterRegistry;
import me.jishuna.jishlib.data.object.DataObject;
import me.jishuna.jishlib.data.object.MapDataObject;
import me.jishuna.jishlib.data.source.yaml.YamlReader;
import me.jishuna.jishlib.data.source.yaml.YamlWriter;

public abstract class ReloadableDataHolder<T> {
    protected final File file;
    protected final List<ConfigField> fields = new ArrayList<>();
    // private final Method postLoadMethod;

    protected ReloadableDataHolder(File file, Class<T> clazz) {
        this.file = file;
        // this.postLoadMethod = findPostLoadMethod(clazz);

        cacheFields(clazz);
    }

    public static <T> ReloadableInstanceDataHolder<T> create(File file, T object) {
        return new ReloadableInstanceDataHolder<>(file, object);
    }

    public static <T> ReloadableStaticDataHolder<T> create(File file, Class<T> clazz) {
        return new ReloadableStaticDataHolder<>(file, clazz);
    }

    public ReloadableDataHolder<T> load() {
        load(true);

        return this;
    }

    public ReloadableDataHolder<T> load(boolean includeStatic) {
        if (!prepareFile()) {
            // File error
            return this;
        }

        YamlReader source = YamlReader.create(".");
        MapDataObject data = source.read(YamlConfiguration.loadConfiguration(this.file));

        for (ConfigField field : this.fields) {
            if (field.isStatic() && !includeStatic) {
                continue;
            }

            String path = field.getPath();

            DataType<?> type = DataType.get(field.getField());
            TypeAdapter<DataObject<?>, ?> adapter = TypeAdapterRegistry.getAdapter(type);
            if (adapter == null) {
                Logger.warn("No data adapter found for {0}, using default value", type.getType());
                continue;
            }

            DataObject<?> saved = data.get(path);
            if ((saved == null) || !adapter.getObjectType().isInstance(saved)) {
                Logger.warn("No saved value found for {0}, using default value", path);
                continue;
            }

            Object readValue = adapter.deserialize(saved);
            if (readValue == null) {
                Logger.warn("Failed to read value for {0}, using default value", path);
                continue;
            }

            if (!setField(field, readValue)) {
                Logger.warn("Failed to set field {0}, using default value", field.getField().getName());
            }
        }

//        if (this.postLoadMethod != null) {
//            this.postLoad(this.postLoadMethod);
//        }
        return this;
    }

    @SuppressWarnings("unchecked")
    public ReloadableDataHolder<T> save(boolean replace) {
        if (!prepareFile()) {
            // File error
            return this;
        }

        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(this.file);
        YamlReader source = YamlReader.create(".");
        MapDataObject data = source.read(configuration);

        for (ConfigField field : this.fields) {
            String path = field.getPath();

            DataType<?> type = DataType.get(field.getField());
            TypeAdapter<DataObject<?>, Object> adapter = (TypeAdapter<DataObject<?>, Object>) TypeAdapterRegistry.getAdapter(type);
            if (adapter == null) {
                Logger.warn("No data adapter found for {0}, cannot save value", type.getType());
                continue;
            }

            Object writeValue = getField(field);
            if (writeValue == null) {
                continue; // Don't save null
            }

            DataObject<?> obj = adapter.serialize(writeValue);
            if (obj == null) {
                Logger.warn("Failed to read field {0}, cannot save value", field.getField().getName());
                continue;
            }

            data.set(path, obj, replace);
        }

        try {
            YamlWriter writer = YamlWriter.create(configuration);
            writer.writeMap("", data);
            this.fields.forEach(f -> configuration.setComments(f.getPath(), f.getComments()));
            writer.save(this.file);
        } catch (IOException ex) {
            Logger.error("Failed to save file {0}: {1}", this.file.getPath(), ex);
        }
        return this;
    }

    protected abstract boolean setField(ConfigField field, Object value);

    protected abstract Object getField(ConfigField field);

    private boolean prepareFile() {
        try {
            File parentFile = this.file.getParentFile();
            if (parentFile != null && !parentFile.exists() && !this.file.getParentFile().mkdirs()) {
                Logger.error("Failed to create file {0}", this.file.getPath());
                return false;
            }

            if (!this.file.exists()) {
                return this.file.createNewFile();
            }

            return true;
        } catch (IOException ex) {
            Logger.error("Failed to create file {0}: {1}", this.file.getPath(), ex);
            return false;
        }
    }

    private void cacheFields(Class<? super T> clazz) {
        int index = 0;
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Path.class)) {
                continue;
            }

            this.fields.add(index++, new ConfigField(field));
        }

        Class<? super T> superClass = clazz.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            cacheFields(superClass);
        }
    }

    /*
     * private Method findPostLoadMethod(Class<? super T> clazz) { for (Method
     * method : clazz.getDeclaredMethods()) { if
     * (method.isAnnotationPresent(PostLoad.class)) { return method; } }
     *
     * Class<? super T> superClass = clazz.getSuperclass(); if (superClass != null
     * && superClass != Object.class) { return findPostLoadMethod(superClass); }
     *
     * return null; }
     */
}

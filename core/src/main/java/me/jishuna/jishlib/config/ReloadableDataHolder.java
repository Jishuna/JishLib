package me.jishuna.jishlib.config;

import me.jishuna.dataapi.adapter.DataType;
import me.jishuna.dataapi.adapter.TypeAdapter;
import me.jishuna.dataapi.adapter.TypeAdapterRegistry;
import me.jishuna.dataapi.data.value.DataValue;
import me.jishuna.dataapi.data.value.collection.MapValue;
import me.jishuna.dataapi.yml.YmlDataAdapter;
import me.jishuna.jishlib.config.annotation.Path;
import me.jishuna.jishlib.util.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class ReloadableDataHolder<T> {
    private static final YmlDataAdapter ADAPTER = YmlDataAdapter.create();

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

        MapValue data;
        try {
            data = ADAPTER.read(this.file);
        } catch (IOException e) {
            e.printStackTrace();
            return this;
        }

        for (ConfigField field : this.fields) {
            if (field.isStatic() && !includeStatic) {
                continue;
            }

            String path = field.getPath();

            DataType<?> type = DataType.get(field.getField());
            TypeAdapter<?> adapter = TypeAdapterRegistry.getAdapter(type);
            if (adapter == null) {
                Logger.warn("No data adapter found for {0}, using default value", type.getType());
                continue;
            }

            Object readValue = data.get(path, adapter);
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

        MapValue data;
        try {
            data = ADAPTER.read(this.file);
        } catch (IOException e) {
            e.printStackTrace();
            return this;
        }

        for (ConfigField field : this.fields) {
            String path = field.getPath();

            DataType<?> type = DataType.get(field.getField());
            TypeAdapter<Object> adapter = (TypeAdapter<Object>) TypeAdapterRegistry.getAdapter(type);
            if (adapter == null) {
                Logger.warn("No data adapter found for {0}, cannot save value", type.getType());
                continue;
            }

            Object writeValue = getField(field);
            if (writeValue == null) {
                continue; // Don't save null
            }

            DataValue<?> obj = adapter.serialize(writeValue);
            if (obj == null) {
                Logger.warn("Failed to read field {0}, cannot save value", field.getField().getName());
                continue;
            }

            data.set(path, obj, replace);
            data.setComments(path, field.getComments());
        }

        try {
            ADAPTER.write(data, file);
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

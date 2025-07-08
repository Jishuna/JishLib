package me.jishuna.jishlib.config;

import java.io.File;
import me.jishuna.jishlib.util.ReflectionHelper;

public class ReloadableInstanceDataHolder<T> extends ReloadableDataHolder<T> {
    private final T wrapped;

    @SuppressWarnings("unchecked")
    protected ReloadableInstanceDataHolder(File file, T object) {
        super(file, (Class<T>) object.getClass());

        this.wrapped = object;
    }

    @Override
    protected boolean setField(ConfigField field, Object value) {
        return ReflectionHelper.setField(field.getField(), value, this.wrapped);
    }

    @Override
    protected Object getField(ConfigField field) {
        return ReflectionHelper.readField(field.getField(), this.wrapped);
    }
}

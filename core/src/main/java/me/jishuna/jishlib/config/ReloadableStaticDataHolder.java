package me.jishuna.jishlib.config;

import java.io.File;
import me.jishuna.jishlib.util.ReflectionHelper;

public class ReloadableStaticDataHolder<T> extends ReloadableDataHolder<T> {

    protected ReloadableStaticDataHolder(File file, Class<T> clazz) {
        super(file, clazz);
    }

    @Override
    protected boolean setField(ConfigField field, Object value) {
        return ReflectionHelper.setField(field.getField(), value, null);
    }

    @Override
    protected Object getField(ConfigField field) {
        return ReflectionHelper.readField(field.getField(), null);
    }
}

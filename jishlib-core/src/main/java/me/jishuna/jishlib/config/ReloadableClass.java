package me.jishuna.jishlib.config;

import java.io.File;

public class ReloadableClass<T> extends ConfigReloadable<T> {

    public ReloadableClass(ConfigurationManager manager, File file, Class<T> clazz) {
        super(manager, file, clazz);
    }

    @Override
    protected void setField(ConfigField field, Object value) throws ReflectiveOperationException {
        if (field.isStatic()) {
            field.getField().set(null, value);
        }
    }

    @Override
    protected Object getField(ConfigField field) {
        if (field.isStatic()) {
            try {
                return field.getField().get(null);
            } catch (ReflectiveOperationException ex) {
                return null;
            }
        }
        return null;
    }
}

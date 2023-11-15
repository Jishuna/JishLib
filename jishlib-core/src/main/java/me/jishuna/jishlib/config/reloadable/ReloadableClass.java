package me.jishuna.jishlib.config.reloadable;

import java.io.File;
import java.lang.reflect.Method;
import me.jishuna.jishlib.config.ConfigField;

public class ReloadableClass<T> extends ConfigReloadable<T> {

    public ReloadableClass(File file, Class<T> clazz) {
        super(file, clazz);
    }

    @Override
    protected void postLoad(Method postLoadMethod) {
        try {
            boolean access = postLoadMethod.canAccess(null);
            if (!access) {
                postLoadMethod.trySetAccessible();
            }

            postLoadMethod.invoke(null);

            if (!access) {
                postLoadMethod.setAccessible(false);
            }
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
        }
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
            }
        }
        return null;
    }
}

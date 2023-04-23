package me.jishuna.jishlib.config;

import java.io.File;
import java.lang.reflect.Field;

public class ReloadableObject<T> extends ConfigReloadable<T> {
    private final T wrapped;

    @SuppressWarnings("unchecked")
    public ReloadableObject(ConfigurationManager manager, File file, T object) {
        super(manager, file, (Class<T>) object.getClass());

        this.wrapped = object;
    }

    @Override
    protected void setField(ConfigField field, Object value) throws ReflectiveOperationException {
        Field internal = field.getField();
        if (field.isStatic()) {
            internal.set(null, value);
        } else {
            boolean access = internal.canAccess(wrapped);
            if (!access) {
                internal.trySetAccessible();
            }

            internal.set(wrapped, value);

            if (!access) {
                internal.setAccessible(false);
            }
        }
    }

    @Override
    protected Object getField(ConfigField field) {
        Field internal = field.getField();
        if (field.isStatic()) {
            try {
                return internal.get(null);
            } catch (ReflectiveOperationException ex) {
                return null;
            }
        } else {
            boolean access = internal.canAccess(wrapped);
            if (!access) {
                internal.trySetAccessible();
            }

            try {
                return internal.get(wrapped);
            } catch (ReflectiveOperationException ex) {
                return null;
            } finally {
                if (!access) {
                    internal.setAccessible(false);
                }
            }
        }
    }

    public T getWrapped() {
        return wrapped;
    }
}

package me.jishuna.jishlib.config.reloadable;

import java.io.File;
import java.lang.reflect.Method;
import me.jishuna.jishlib.config.ConfigField;
import me.jishuna.jishlib.config.ConfigurationManager;
import me.jishuna.jishlib.config.ReflectionHelper;

public class ReloadableObject<T> extends ConfigReloadable<T> {
    private final T wrapped;

    @SuppressWarnings("unchecked")
    public ReloadableObject(ConfigurationManager manager, File file, T object) {
        super(manager, file, (Class<T>) object.getClass());

        this.wrapped = object;
    }

    @Override
    protected void postLoad(Method postLoadMethod) {
        try {
            boolean access = postLoadMethod.canAccess(wrapped);
            if (!access) {
                postLoadMethod.trySetAccessible();
            }

            postLoadMethod.invoke(this.wrapped);

            if (!access) {
                postLoadMethod.setAccessible(false);
            }
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void setField(ConfigField field, Object value) throws ReflectiveOperationException {
        ReflectionHelper.setField(field, value, this.wrapped);
    }

    @Override
    protected Object getField(ConfigField field) {
        return ReflectionHelper.getField(field, this.wrapped);
    }

    public T getWrapped() {
        return wrapped;
    }
}

package me.jishuna.jishlib.config;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import me.jishuna.jishlib.config.annotation.PostLoad;

public class ReloadableObject<T> extends ConfigReloadable<T> {
    private final T wrapped;
    private final Method postLoad;

    @SuppressWarnings("unchecked")
    public ReloadableObject(ConfigurationManager manager, File file, T object) {
        super(manager, file, (Class<T>) object.getClass());

        this.wrapped = object;
        this.postLoad = findPostLoadMethod((Class<? super T>) object.getClass());
    }

    @Override
    public ConfigReloadable<T> load(boolean includeStatic) {
        ConfigReloadable<T> reloadable = super.load(includeStatic);

        if (this.postLoad != null) {
            try {
                boolean access = this.postLoad.canAccess(wrapped);
                if (!access) {
                    this.postLoad.trySetAccessible();
                }

                this.postLoad.invoke(this.wrapped);

                if (!access) {
                    this.postLoad.setAccessible(false);
                }
            } catch (ReflectiveOperationException ex) {
                ex.printStackTrace();
            }
        }
        return reloadable;
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

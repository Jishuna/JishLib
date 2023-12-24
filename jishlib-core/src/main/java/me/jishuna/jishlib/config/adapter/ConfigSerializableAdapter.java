package me.jishuna.jishlib.config.adapter;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import me.jishuna.jishlib.config.ConfigSerializable;
import me.jishuna.jishlib.config.ConfigType;

public class ConfigSerializableAdapter<S, R extends ConfigSerializable<S>> implements TypeAdapter<S, R> {

    private final Class<R> clazz;
    private Class<S> savedClass;

    @SuppressWarnings("unchecked")
    public ConfigSerializableAdapter(ConfigType<?> type) {
        this.clazz = (Class<R>) type.getType();

        for (Type interfaceType : this.clazz.getGenericInterfaces()) {
            ConfigType<?> configType = ConfigType.create(interfaceType);
            if (configType.getType() == ConfigSerializable.class) {
                this.savedClass = (Class<S>) configType.getComponentTypes().get(0).getType();
                break;
            }
        }
    }

    @Override
    public Class<S> getSavedType() {
        return this.savedClass;
    }

    @Override
    public Class<R> getRuntimeType() {
        return this.clazz;
    }

    @Override
    public R read(S value) {
        try {
            Method deserializeMethod = this.clazz.getDeclaredMethod("deserialize", this.savedClass);
            return this.clazz.cast(deserializeMethod.invoke(null, value));
        } catch (ReflectiveOperationException | ClassCastException e) {
            return null;
        }
    }

    @Override
    public S write(R value, S existing, boolean replace) {
        return value.serialize(existing, replace);
    }
}

package me.jishuna.jishlib.pdc;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

public class EnumType<T extends Enum<T>> implements PersistentDataType<String, T> {
    private final Class<T> clazz;

    public EnumType(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Class<String> getPrimitiveType() {
        return String.class;
    }

    @Override
    public Class<T> getComplexType() {
        return this.clazz;
    }

    @Override
    public String toPrimitive(T complex, PersistentDataAdapterContext context) {
        return complex.name();
    }

    @Override
    public T fromPrimitive(String primitive, PersistentDataAdapterContext context) {
        return Enum.valueOf(this.clazz, primitive);
    }
}

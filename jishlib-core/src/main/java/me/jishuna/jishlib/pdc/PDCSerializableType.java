package me.jishuna.jishlib.pdc;

import java.util.function.BiFunction;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PDCSerializableType<T extends PDCSerializable> implements PersistentDataType<PersistentDataContainer, T> {
    private final Class<T> type;
    private final BiFunction<PersistentDataContainer, PersistentDataAdapterContext, T> creator;

    public PDCSerializableType(Class<T> type, BiFunction<PersistentDataContainer, PersistentDataAdapterContext, T> creator) {
        this.type = type;
        this.creator = creator;
    }

    @Override
    public Class<PersistentDataContainer> getPrimitiveType() {
        return PersistentDataContainer.class;
    }

    @Override
    public Class<T> getComplexType() {
        return this.type;
    }

    @Override
    public PersistentDataContainer toPrimitive(T complex, PersistentDataAdapterContext context) {
        return complex.toContainer(context);
    }

    @Override
    public T fromPrimitive(PersistentDataContainer primitive, PersistentDataAdapterContext context) {
        return this.creator.apply(primitive, context);
    }

}

package me.jishuna.jishlib.pdc;

import com.google.common.base.Supplier;
import java.util.Collection;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class CollectionType<T extends Collection<V>, V> implements PersistentDataType<PersistentDataContainer, T> {
    private final Supplier<T> supplier;
    private final Class<T> clazz;
    private final PersistentDataType<?, V> type;

    @SuppressWarnings("unchecked")
    public CollectionType(Supplier<T> supplier, PersistentDataType<?, V> type) {
        this.supplier = supplier;
        this.clazz = (Class<T>) supplier.get().getClass();
        this.type = type;
    }

    @Override
    public Class<PersistentDataContainer> getPrimitiveType() {
        return PersistentDataContainer.class;
    }

    @Override
    public Class<T> getComplexType() {
        return this.clazz;
    }

    @Override
    public PersistentDataContainer toPrimitive(T complex, PersistentDataAdapterContext context) {
        PersistentDataContainer container = context.newPersistentDataContainer();

        int index = 0;
        for (V value : complex) {
            if (value == null) {
                continue;
            }

            container.set(KeyHelper.getValueKey(index++), this.type, value);
        }

        return container;
    }

    @Override
    public T fromPrimitive(PersistentDataContainer primitive, PersistentDataAdapterContext context) {
        T collection = this.supplier.get();

        for (NamespacedKey key : primitive.getKeys()) {
            V value = primitive.get(key, this.type);
            if (value != null) {
                collection.add(value);
            }
        }

        return collection;
    }
}

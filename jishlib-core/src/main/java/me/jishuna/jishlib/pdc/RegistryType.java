package me.jishuna.jishlib.pdc;

import java.util.function.Function;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import me.jishuna.jishlib.datastructure.Registry;

public class RegistryType<K, V, I> implements PersistentDataType<K, V> {
    private final Class<K> primitive;
    private final Class<V> complex;
    private final PersistentDataType<K, I> type;
    private final Function<V, I> keyFunction;
    private final Registry<I, V> registry;

    public RegistryType(Class<V> complex, PersistentDataType<K, I> type, Function<V, I> keyFunction, Registry<I, V> registry) {
        this.primitive = type.getPrimitiveType();
        this.complex = complex;
        this.type = type;
        this.keyFunction = keyFunction;
        this.registry = registry;
    }

    @Override
    public Class<K> getPrimitiveType() {
        return this.primitive;
    }

    @Override
    public Class<V> getComplexType() {
        return this.complex;
    }

    @Override
    public K toPrimitive(V complex, PersistentDataAdapterContext context) {
        return this.type.toPrimitive(this.keyFunction.apply(complex), context);
    }

    @Override
    public V fromPrimitive(K primitive, PersistentDataAdapterContext context) {
        I key = this.type.fromPrimitive(primitive, context);
        return this.registry.get(key);
    }
}

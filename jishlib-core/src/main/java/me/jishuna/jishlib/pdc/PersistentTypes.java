package me.jishuna.jishlib.pdc;

import java.util.function.BiFunction;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

public class PersistentTypes {
    public static final PersistentDataType<Byte, Boolean> BOOLEAN = new BooleanType();
    public static final PersistentDataType<Double, Double> DOUBLE = PersistentDataType.DOUBLE;
    public static final PersistentDataType<Float, Float> FLOAT = PersistentDataType.FLOAT;
    public static final PersistentDataType<Integer, Integer> INTEGER = PersistentDataType.INTEGER;
    public static final PersistentDataType<Long, Long> LONG = PersistentDataType.LONG;
    public static final PersistentDataType<String, NamespacedKey> NAMESPACE = new NamespacedKeyType();
    public static final PersistentDataType<Short, Short> SHORT = PersistentDataType.SHORT;
    public static final PersistentDataType<String, String> STRING = PersistentDataType.STRING;
    public static final PersistentDataType<Integer, Vector> VECTOR = new VectorType();

    private PersistentTypes() {
    }

    public static <T extends PDCSerializable> CustomType<T> create(Class<T> clazz, BiFunction<PersistentDataContainer, PersistentDataAdapterContext, T> creator) {
        return new CustomType<>(clazz, creator);
    }
}

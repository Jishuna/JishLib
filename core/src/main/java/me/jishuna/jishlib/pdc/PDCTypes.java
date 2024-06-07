package me.jishuna.jishlib.pdc;

import java.util.UUID;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public final class PDCTypes {

    public static final PersistentDataType<Byte, Byte> BYTE = PersistentDataType.BYTE;
    public static final PersistentDataType<byte[], byte[]> BYTE_ARRAY = PersistentDataType.BYTE_ARRAY;
    public static final PersistentDataType<Short, Short> SHORT = PersistentDataType.SHORT;
    public static final PersistentDataType<Integer, Integer> INT = PersistentDataType.INTEGER;
    public static final PersistentDataType<int[], int[]> INT_ARRAY = PersistentDataType.INTEGER_ARRAY;
    public static final PersistentDataType<Long, Long> LONG = PersistentDataType.LONG;
    public static final PersistentDataType<long[], long[]> LONG_ARRAY = PersistentDataType.LONG_ARRAY;
    public static final PersistentDataType<Float, Float> FLOAT = PersistentDataType.FLOAT;
    public static final PersistentDataType<Double, Double> DOUBLE = PersistentDataType.DOUBLE;
    public static final PersistentDataType<Byte, Boolean> BOOLEAN = PersistentDataType.BOOLEAN;

    public static final PersistentDataType<String, String> STRING = PersistentDataType.STRING;
    public static final PersistentDataType<PersistentDataContainer, PersistentDataContainer> CONTAINER = PersistentDataType.TAG_CONTAINER;

    public static final PersistentDataType<String, NamespacedKey> NAMESPACED_KEY = new NamespacedKeyPersistentDataType();
    public static final PersistentDataType<long[], UUID> UUID = new UUIDPersistentDataType();
    public static final PersistentDataType<byte[], ItemStack> ITEMSTACK = new ItemStackPersistentDataType();

    private PDCTypes() {
    }
}

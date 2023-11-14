package me.jishuna.jishlib.pdc;

import java.util.UUID;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

public class UUIDType implements PersistentDataType<long[], UUID> {

    @Override
    public Class<long[]> getPrimitiveType() {
        return long[].class;
    }

    @Override
    public Class<UUID> getComplexType() {
        return UUID.class;
    }

    @Override
    public long[] toPrimitive(UUID complex, PersistentDataAdapterContext context) {
        long[] array = new long[2];
        array[0] = complex.getMostSignificantBits();
        array[1] = complex.getLeastSignificantBits();

        return array;
    }

    @Override
    public UUID fromPrimitive(long[] primitive, PersistentDataAdapterContext context) {
        return new UUID(primitive[0], primitive[1]);
    }
}

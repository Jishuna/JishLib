package me.jishuna.jishlib.pdc;

import java.util.UUID;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

public class UUIDPersistentDataType implements PersistentDataType<long[], UUID> {

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
        return new long[] { complex.getMostSignificantBits(), complex.getLeastSignificantBits() };
    }

    @Override
    public UUID fromPrimitive(long[] primitive, PersistentDataAdapterContext context) {
        return new UUID(primitive[0], primitive[1]);
    }
}

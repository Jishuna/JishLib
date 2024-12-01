package me.jishuna.jishlib.pdc;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class UUIDPersistentDataType implements PersistentDataType<long[], UUID> {

    @Override
    public @NotNull Class<long[]> getPrimitiveType() {
        return long[].class;
    }

    @Override
    public @NotNull Class<UUID> getComplexType() {
        return UUID.class;
    }

    @Override
    public long @NotNull [] toPrimitive(UUID complex, @NotNull PersistentDataAdapterContext context) {
        return new long[]{complex.getMostSignificantBits(), complex.getLeastSignificantBits()};
    }

    @Override
    public @NotNull UUID fromPrimitive(long[] primitive, @NotNull PersistentDataAdapterContext context) {
        return new UUID(primitive[0], primitive[1]);
    }
}

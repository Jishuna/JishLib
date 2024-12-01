package me.jishuna.jishlib.pdc;

import me.jishuna.jishlib.util.Key;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class KeyPersistentDataType implements PersistentDataType<String, Key> {

    @Override
    public @NotNull Class<String> getPrimitiveType() {
        return String.class;
    }

    @Override
    public @NotNull Class<Key> getComplexType() {
        return Key.class;
    }

    @Override
    public @NotNull String toPrimitive(Key complex, @NotNull PersistentDataAdapterContext context) {
        return complex.toString();
    }

    @Override
    public @NotNull Key fromPrimitive(@NotNull String primitive, @NotNull PersistentDataAdapterContext context) {
        return Key.of(primitive); // TODO Dang you nullability
    }
}

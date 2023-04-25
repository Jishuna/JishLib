package me.jishuna.jishlib.pdc;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

public class NamespacedKeyType implements PersistentDataType<String, NamespacedKey> {

    @Override
    public Class<String> getPrimitiveType() {
        return String.class;
    }

    @Override
    public Class<NamespacedKey> getComplexType() {
        return NamespacedKey.class;
    }

    @Override
    public String toPrimitive(NamespacedKey complex, PersistentDataAdapterContext context) {
        return complex.toString();
    }

    @Override
    public NamespacedKey fromPrimitive(String primitive, PersistentDataAdapterContext context) {
        return NamespacedKey.fromString(primitive);
    }
}

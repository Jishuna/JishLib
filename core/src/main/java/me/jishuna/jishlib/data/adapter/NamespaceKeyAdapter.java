package me.jishuna.jishlib.data.adapter;

import org.bukkit.NamespacedKey;

public class NamespaceKeyAdapter implements TypeAdapterString<NamespacedKey> {

    @Override
    public NamespacedKey fromString(String value) {
        return NamespacedKey.fromString(value);
    }

    @Override
    public String toString(NamespacedKey value) {
        return value.toString();
    }

}

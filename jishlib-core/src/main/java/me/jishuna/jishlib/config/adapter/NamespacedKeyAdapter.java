package me.jishuna.jishlib.config.adapter;

import org.bukkit.NamespacedKey;

public class NamespacedKeyAdapter implements GenericStringAdapter<NamespacedKey> {

    @Override
    public Class<NamespacedKey> getRuntimeType() {
        return NamespacedKey.class;
    }

    @Override
    public String toString(NamespacedKey value) {
        return value.toString();
    }

    @Override
    public NamespacedKey fromString(String value) {
        return NamespacedKey.fromString(value);
    }
}

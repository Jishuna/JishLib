package me.jishuna.jishlib.data.adapter;

import org.bukkit.NamespacedKey;
import me.jishuna.jishlib.data.holder.StringDataHolder;

public class NamespaceKeyAdapter implements TypeAdapter<StringDataHolder, NamespacedKey> {

    @Override
    public Class<StringDataHolder> getObjectType() {
        return StringDataHolder.class;
    }

    @Override
    public NamespacedKey deserialize(StringDataHolder data) {
        return fromString(data.get());
    }

    @Override
    public StringDataHolder serialize(NamespacedKey value) {
        return StringDataHolder.of(toString(value));
    }

    @Override
    public NamespacedKey fromString(String value) {
        return NamespacedKey.fromString(value);
    }

    @Override
    public String toString(NamespacedKey value) {
        return value.toString();
    }
}

package me.jishuna.jishlib.data.adapter;

import org.bukkit.NamespacedKey;
import me.jishuna.jishlib.data.object.StringDataObject;

public class NamespaceKeyAdapter implements TypeAdapter<StringDataObject, NamespacedKey> {

    @Override
    public Class<StringDataObject> getObjectType() {
        return StringDataObject.class;
    }

    @Override
    public NamespacedKey deserialize(StringDataObject data) {
        return fromString(data.get());
    }

    @Override
    public StringDataObject serialize(NamespacedKey value) {
        return StringDataObject.of(toString(value));
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

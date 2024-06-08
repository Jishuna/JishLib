package me.jishuna.jishlib.data.adapter;

import org.bukkit.Material;
import me.jishuna.jishlib.data.object.StringDataObject;

public class MaterialAdapter implements TypeAdapter<StringDataObject, Material> {
    @Override
    public Class<StringDataObject> getObjectType() {
        return StringDataObject.class;
    }

    @Override
    public Material deserialize(StringDataObject data) {
        return fromString(data.get());
    }

    @Override
    public StringDataObject serialize(Material value) {
        return StringDataObject.of(toString(value));
    }

    @Override
    public Material fromString(String value) {
        return Material.matchMaterial(value);
    }

    @Override
    public String toString(Material value) {
        return value.getKey().toString();
    }
}

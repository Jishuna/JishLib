package me.jishuna.jishlib.data.adapter;

import org.bukkit.Material;
import me.jishuna.jishlib.data.holder.StringDataHolder;

public class MaterialAdapter implements TypeAdapter<StringDataHolder, Material> {
    @Override
    public Class<StringDataHolder> getObjectType() {
        return StringDataHolder.class;
    }

    @Override
    public Material deserialize(StringDataHolder data) {
        return fromString(data.get());
    }

    @Override
    public StringDataHolder serialize(Material value) {
        return StringDataHolder.of(toString(value));
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

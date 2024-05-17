package me.jishuna.jishlib.data.adapter;

import org.bukkit.Material;

public class MaterialAdapter implements TypeAdapterString<Material> {

    @Override
    public Material fromString(String value) {
        return Material.matchMaterial(value);
    }

    @Override
    public String toString(Material value) {
        return value.getKey().toString();
    }

}

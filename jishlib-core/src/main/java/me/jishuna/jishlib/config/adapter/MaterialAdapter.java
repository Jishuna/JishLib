package me.jishuna.jishlib.config.adapter;

import org.bukkit.Material;

public class MaterialAdapter implements GenericStringAdapter<Material> {

    @Override
    public Class<Material> getRuntimeType() {
        return Material.class;
    }

    @Override
    public String toString(Material value) {
        return value.getKey().toString();
    }

    @Override
    public Material fromString(String value) {
        return Material.matchMaterial(value);
    }
}

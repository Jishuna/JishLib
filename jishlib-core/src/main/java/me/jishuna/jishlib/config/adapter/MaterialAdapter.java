package me.jishuna.jishlib.config.adapter;

import org.bukkit.Material;

public class MaterialAdapter implements StringAdapter<Material> {

    @Override
    public String toString(Material value) {
        return value.getKey().toString();
    }

    @Override
    public Material fromString(String value) {
        return Material.matchMaterial(value);
    }
}

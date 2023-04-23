package me.jishuna.jishlib.config.adapter;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

public class MaterialTypeAdapter implements TypeAdapter<Material> {

    public Material read(ConfigurationSection config, String path) {
        return Material.matchMaterial(config.getString(path));
    }

    public void write(ConfigurationSection config, String path, Object value) {
        config.set(path, ((Material) value).name());
    }
}

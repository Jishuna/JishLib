package me.jishuna.jishlib.config.adapter;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;
import me.jishuna.jishlib.config.ConfigAPI;
import me.jishuna.jishlib.config.CustomConfig;

public class VectorAdapter implements TypeAdapter<ConfigurationSection, Vector> {

    @Override
    public Class<ConfigurationSection> getSavedType() {
        return ConfigurationSection.class;
    }

    @Override
    public Class<Vector> getRuntimeType() {
        return Vector.class;
    }

    @Override
    public Vector read(ConfigurationSection value) {
        return new Vector(value.getDouble("x", 0), value.getDouble("y", 0), value.getDouble("z", 0));
    }

    @Override
    public ConfigurationSection write(Vector value, ConfigurationSection existing, boolean replace) {
        if (existing == null) {
            existing = new CustomConfig();
        }

        ConfigAPI.setIfAbsent(existing, "x", value::getX);
        ConfigAPI.setIfAbsent(existing, "y", value::getY);
        ConfigAPI.setIfAbsent(existing, "z", value::getZ);

        return existing;
    }
}

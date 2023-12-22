package me.jishuna.jishlib.config.adapter;

import org.bukkit.Color;

public class ColorAdapter implements GenericStringAdapter<Color> {

    @Override
    public Class<Color> getRuntimeType() {
        return Color.class;
    }

    @Override
    public String toString(Color value) {
        return "#" + Integer.toHexString(value.asRGB());
    }

    @Override
    public Color fromString(String value) {
        if (value.startsWith("#")) {
            value = value.substring(1);
        }

        return Color.fromRGB(Integer.parseInt(value, 16));
    }
}

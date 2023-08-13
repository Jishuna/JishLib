package me.jishuna.jishlib.util;

import org.bukkit.Color;

import me.jishuna.jishlib.Constants;
import net.kyori.adventure.text.Component;

public class StringUtils {
    private StringUtils() {
    }

    public static Component fromMiniMessage(String input) {
        return Constants.MINI_MESSAGE.deserialize(input);
    }

    public static String toMiniMessage(Component component) {
        return Constants.MINI_MESSAGE.serialize(component);
    }

    public static String toLegacy(Component component) {
        return Constants.SERIALIZER.serialize(component);
    }

    public static Component fromLegacy(String input) {
        return Constants.SERIALIZER.deserialize(input);
    }

    public static String miniMessageToLegacy(String input) {
        return toLegacy(fromMiniMessage(input));
    }

    public static String legacyToMiniMessage(String input) {
        return toMiniMessage(fromLegacy(input));
    }

    public static String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    public static String capitalizeAll(String input) {
        String[] parts = input.split(" ");
        StringBuilder builder = new StringBuilder();

        for (String part : parts) {
            builder.append(capitalize(part));
            builder.append(" ");
        }
        return builder.toString().trim();
    }

    public static Color parseColor(String input, Color def) {
        if (input == null) {
            return def;
        }

        String[] parts = input.split(",");
        int red;
        int green;
        int blue;

        if (parts.length < 3) {
            return def;
        }

        try {
            red = Integer.parseInt(parts[0]);
            green = Integer.parseInt(parts[1]);
            blue = Integer.parseInt(parts[2]);
        } catch (NumberFormatException ex) {
            return def;
        }

        if (!Utils.isWithinBounds(red, 0, 255) || !Utils.isWithinBounds(green, 0, 255) || !Utils.isWithinBounds(blue, 0, 255)) {
            return def;
        }

        return Color.fromRGB(red, green, blue);
    }
}

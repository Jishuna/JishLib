package me.jishuna.jishlib.util;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import me.jishuna.jishlib.Constants;

public class StringUtils {
    private StringUtils() {
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

    public static Component fromLegacy(String input) {
        return Constants.SERIALIZER.deserialize(input);
    }

    public static Component fromMiniMessage(String input) {
        return Constants.MINI_MESSAGE.deserialize(input);
    }

    public static String legacyToMiniMessage(String input) {
        return toMiniMessage(fromLegacy(input));
    }

    public static String miniMessageToLegacy(String input) {
        return ChatColor.translateAlternateColorCodes('&', toLegacy(fromMiniMessage(input)));
    }

    public static String toLegacy(Component component) {
        return Constants.SERIALIZER.serialize(component);
    }

    public static String toMiniMessage(Component component) {
        return Constants.MINI_MESSAGE.serialize(component);
    }
}

package me.jishuna.jishlib.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import me.jishuna.jishlib.Constants;

public final class StringUtils {
    /**
     * Converts the first letter of a string to uppercase and the rest to lowercase.
     *
     * @param input the string to capitalize
     * @return the capitalized string
     */
    public static String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    /**
     * Converts the first letter of each word in a string to uppercase and the rest
     * to lowercase. <br>
     * The string is split on spaces to obtain each word.
     *
     * @param input the string to capitalize
     * @return the capitalized string
     */
    public static String capitalizeAll(String input) {
        String[] parts = input.split(" ");
        StringBuilder builder = new StringBuilder();

        for (String part : parts) {
            builder.append(capitalize(part));
            builder.append(" ");
        }
        return builder.toString().trim();
    }

    public static String formatObject(Object object) {
        return capitalizeAll(object.toString().replace('_', ' '));
    }

    /**
     * Converts a legacy string into a {@link Component}.
     *
     * @param input the input string
     * @return a {@link Component} representing the input string
     */
    public static Component fromLegacy(String input) {
        return Constants.LEGACY_SERIALIZER.deserialize(input);
    }

    /**
     * Converts a {@link MiniMessage} string into a {@link Component}.
     *
     * @param input the input string
     * @return a {@link Component} representing the input string
     */
    public static Component fromMiniMessage(String input) {
        return Constants.MINI_MESSAGE.deserialize(input);
    }

    /**
     * Converts a legacy string into a {@link MiniMessage} string.
     *
     * @param input the input string
     * @return a {@link MiniMessage} string representing the input string
     */
    public static String legacyToMiniMessage(String input) {
        if (input == null) {
            return input;
        }

        return toMiniMessage(fromLegacy(input));
    }

    /**
     * Converts a {@link MiniMessage} string into a legacy string.
     *
     * @param input the input string
     * @return a legacy string representing the input string
     */
    public static String miniMessageToLegacy(String input) {
        if (input == null) {
            return input;
        }

        return ChatColor.translateAlternateColorCodes('&', toLegacy(fromMiniMessage(input)));
    }

    /**
     * Converts a {@link Component} into a legacy string.
     *
     * @param component the input component
     * @return a legacy string representing the input string
     */
    public static String toLegacy(Component component) {
        return Constants.LEGACY_SERIALIZER.serialize(component);
    }

    /**
     * Converts a {@link Component} into a {@link MiniMessage} string.
     *
     * @param component the input component
     * @return a {@link MiniMessage} string representing the input string
     */
    public static String toMiniMessage(Component component) {
        return Constants.MINI_MESSAGE.serialize(component);
    }

    public static boolean containsIgnoreCase(String input, String match) {
        if (input == null || match == null) {
            return false;
        }

        final int length = match.length();
        if (length == 0) {
            return true;
        }

        for (int i = input.length() - length; i >= 0; i--) {
            if (input.regionMatches(true, i, match, 0, length)) {
                return true;
            }
        }
        return false;
    }

    public static BaseComponent combineComponents(BaseComponent[] components) {
        BaseComponent parent = components[0];
        if (components.length == 1) {
            return parent;
        }

        for (int i = 1; i < components.length; i++) {
            parent.addExtra(components[i]);
        }

        return parent;
    }

    private StringUtils() {
    }
}

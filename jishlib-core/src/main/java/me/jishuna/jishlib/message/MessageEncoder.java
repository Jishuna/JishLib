package me.jishuna.jishlib.message;

import java.util.List;
import net.md_5.bungee.api.ChatColor;

public class MessageEncoder {

    public static MessageEntry encode(String key, List<String> values, String[] comments) {
        StringBuilder builder = new StringBuilder("[");
        for (String line : values) {
            line = line.replace(ChatColor.COLOR_CHAR, '&');
            if (line.isEmpty()) {
                line = " ";
            }

            builder.append("\"").append(line).append("\"").append(", ");
        }

        if (builder.length() > 1) {
            builder.delete(builder.length() - 2, builder.length());
        }
        builder.append("]");

        return new MessageEntry(key + ": " + builder.toString(), comments);
    }

    public static MessageEntry encode(String key, String value, String[] comments) {
        return new MessageEntry(key + ": " + value.replace(ChatColor.COLOR_CHAR, '&'), comments);
    }
}

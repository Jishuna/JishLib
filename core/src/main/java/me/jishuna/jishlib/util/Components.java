package me.jishuna.jishlib.util;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import me.jishuna.jishlib.Constants;
import me.jishuna.jishlib.Plugin;

public class Components {
    private static final BukkitAudiences ADVENTURE = BukkitAudiences.create(Plugin.getInstance());

    public static void sendMessage(CommandSender sender, Component component) {
        ADVENTURE.sender(sender).sendMessage(component);
    }

    public static void sendActionBar(CommandSender sender, Component component) {
        ADVENTURE.sender(sender).sendActionBar(component);
    }

    public static String toString(Component component) {
        return Constants.LEGACY_SERIALIZER.serialize(component);
    }
}

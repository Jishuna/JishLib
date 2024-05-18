package me.jishuna.jishlib.util;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.meta.ItemMeta;
import me.jishuna.jishlib.Constants;
import me.jishuna.jishlib.Plugin;
import me.jishuna.jishlib.nms.NMS;

public class Components {
    private static final BukkitAudiences ADVENTURE = BukkitAudiences.create(Plugin.getInstance());

    public static void setItemName(ItemMeta meta, Component component) {
        if (NMS.isInitialized()) {
            NMS.get().setItemNameComponent(meta, component);
        } else {
            meta.setDisplayName(Constants.LEGACY_SERIALIZER.serialize(component));
        }
    }

    public static void sendMessage(CommandSender sender, Component component) {
        ADVENTURE.sender(sender).sendMessage(component);
    }
}

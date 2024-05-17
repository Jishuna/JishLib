package me.jishuna.jishlib.util;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.meta.ItemMeta;
import me.jishuna.jishlib.Constants;
import me.jishuna.jishlib.nms.NMS;

public class Components {
    public static void setItemName(ItemMeta meta, Component component) {
        if (NMS.isInitialized()) {
            NMS.get().setItemNameComponent(meta, component);
        } else {
            meta.setDisplayName(Constants.LEGACY_SERIALIZER.serialize(component));
        }
    }
}

package me.jishuna.jishlib.util;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class ItemUtils {
    private ItemUtils() {
    }

    public static <T, V> V getPersistantData(ItemStack item, NamespacedKey key, PersistentDataType<T, V> type) {
        if (item == null || !item.hasItemMeta()) {
            return null;
        }

        return item.getItemMeta().getPersistentDataContainer().get(key, type);
    }
}

package me.jishuna.jishlib.pdc;

import java.util.function.BiFunction;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

public final class PDCUtils {
    public static <C> C get(NamespacedKey key, PersistentDataType<?, C> type, PersistentDataHolder holder) {
        return holder.getPersistentDataContainer().get(key, type);
    }

    public static <C> C get(NamespacedKey key, PersistentDataType<?, C> type, PersistentDataHolder holder, C def) {
        return holder.getPersistentDataContainer().getOrDefault(key, type, def);
    }

    public static <C> C get(NamespacedKey key, PersistentDataType<?, C> type, ItemStack item) {
        return get(key, type, item, null);
    }

    public static <C> C get(NamespacedKey key, PersistentDataType<?, C> type, ItemStack item, C def) {
        if (item == null || !item.hasItemMeta()) {
            return def;
        }
        return item.getItemMeta().getPersistentDataContainer().getOrDefault(key, type, def);
    }

    public static <C> void set(NamespacedKey key, PersistentDataType<?, C> type, PersistentDataHolder holder, C value) {
        holder.getPersistentDataContainer().set(key, type, value);
    }

    public static <C> void set(NamespacedKey key, PersistentDataType<?, C> type, ItemStack item, C value) {
        if (item == null || !item.hasItemMeta()) {
            return;
        }
        item.getItemMeta().getPersistentDataContainer().set(key, type, value);
    }

    public static <T extends PDCSerializable> PDCSerializableType<T> createType(Class<T> clazz, BiFunction<PersistentDataContainer, PersistentDataAdapterContext, T> creator) {
        return new PDCSerializableType<>(clazz, creator);
    }

    private PDCUtils() {
    }
}

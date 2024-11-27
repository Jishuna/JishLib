package me.jishuna.jishlib.adapter;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;

public interface Adapter {
    static Adapter get() {
        return AdapterInstance.getAdapter();
    }

    void openInventory(HumanEntity player, Inventory inventory, Component name);

    Collection<Component> getLore(ItemMeta meta);

    void setLore(ItemMeta meta, Collection<Component> lore);

    Component getName(ItemMeta meta);

    void setName(ItemMeta meta, Component name);

    byte[] serializeItem(ItemStack item);

    ItemStack deserializeItem(byte[] bytes);
}

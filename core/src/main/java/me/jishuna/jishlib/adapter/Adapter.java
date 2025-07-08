package me.jishuna.jishlib.adapter;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;

public interface Adapter {
    String getAdapterName();

    Collection<Component> getLore(ItemMeta meta);

    void setLore(ItemMeta meta, Collection<Component> lore);

    Component getName(ItemMeta meta);

    void setName(ItemMeta meta, Component name);

    Component getDisplayName(ItemMeta meta);

    void setDisplayName(ItemMeta meta, Component name);

    void openInventory(HumanEntity target, Inventory inventory, Component title);
}

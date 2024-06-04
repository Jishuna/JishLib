package me.jishuna.jishlib.nms;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;

public interface NMSAdapter {
    public void openInventory(HumanEntity player, Inventory inventory, Component component);

    public void setItemNameComponent(ItemMeta meta, Component component);

    public void addItemLoreComponents(ItemMeta meta, Component... lore);

    public int getCurrentTick();
}

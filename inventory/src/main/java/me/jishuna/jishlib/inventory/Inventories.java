package me.jishuna.jishlib.inventory;

import org.bukkit.entity.HumanEntity;

public class Inventories {
    public static InventorySession openInventory(HumanEntity entity, CustomInventory inventory) {
        return InventoryManager.instance().openInventory(entity, inventory);
    }
}

package me.jishuna.jishlib.inventory;

import java.util.HashMap;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Listener;
import org.bukkit.inventory.InventoryView;

public class CustomInventoryManager implements Listener {
    private final HashMap<InventoryView, CustomInventory<?>> inventoryMap = new HashMap<>();

    public void openInventory(HumanEntity player, CustomInventory<?> inventory) {
        this.inventoryMap.put(player.openInventory(inventory.getBukkitInventory()), inventory);
    }

    public CustomInventory<?> getInventory(InventoryView view) {
        return this.inventoryMap.get(view);
    }

    public CustomInventory<?> removeInventory(InventoryView view) {
        return this.inventoryMap.remove(view);
    }
}

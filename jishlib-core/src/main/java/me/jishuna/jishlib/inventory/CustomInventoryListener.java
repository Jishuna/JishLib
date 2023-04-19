package me.jishuna.jishlib.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CustomInventoryListener implements Listener {
    private final CustomInventoryManager manager;

    public CustomInventoryListener(CustomInventoryManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null)
            return;

        CustomInventory inventory = manager.getInventory(event.getView());

        if (inventory != null) {
            inventory.consumeClickEvent(event);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event) {
        CustomInventory inventory = manager.removeInventory(event.getView());

        if (inventory != null) {
            inventory.consumeCloseEvent(event);
        }
    }
}

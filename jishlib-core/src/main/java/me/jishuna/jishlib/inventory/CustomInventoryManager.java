package me.jishuna.jishlib.inventory;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Listener;

public class CustomInventoryManager implements Listener {
    private final HashMap<UUID, CustomInventorySession> inventoryMap = new HashMap<>();

    public void destroySession(HumanEntity player) {
        destroySession(player.getUniqueId());
    }

    public void destroySession(UUID id) {
        this.inventoryMap.remove(id);
    }

    public CustomInventorySession getSession(HumanEntity player) {
        return getSession(player.getUniqueId());
    }

    public CustomInventorySession getSession(UUID id) {
        return this.inventoryMap.get(id);
    }

    public void openInventory(HumanEntity player, CustomInventory<?> inventory, boolean recordHistory) {
        this.inventoryMap
                .computeIfAbsent(player.getUniqueId(), k -> new CustomInventorySession(player, inventory, this))
                .open(inventory, recordHistory);
    }
}

package me.jishuna.jishlib.inventory;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Listener;
import me.jishuna.jishlib.inventory.session.InventorySession;

public class CustomInventoryManager implements Listener {
    private final HashMap<UUID, InventorySession> inventoryMap = new HashMap<>();

    public void destroySession(HumanEntity player) {
        destroySession(player.getUniqueId());
    }

    public void destroySession(UUID id) {
        this.inventoryMap.remove(id);
    }

    public InventorySession getSession(HumanEntity player) {
        return getSession(player.getUniqueId());
    }

    public InventorySession getSession(UUID id) {
        return this.inventoryMap.get(id);
    }

    public void openInventory(HumanEntity player, CustomInventory<?> inventory, boolean recordHistory) {
        this.inventoryMap
                .computeIfAbsent(player.getUniqueId(), k -> new InventorySession(player, inventory, this))
                .open(inventory, recordHistory);
    }

    public void openInventory(HumanEntity player, CustomInventory<?> inventory, InventorySession initialSession) {
        if (initialSession == null) {
            openInventory(player, inventory, true);
            return;
        }

        this.inventoryMap.put(player.getUniqueId(), initialSession);
        initialSession.changeTo(inventory, false);
    }
}

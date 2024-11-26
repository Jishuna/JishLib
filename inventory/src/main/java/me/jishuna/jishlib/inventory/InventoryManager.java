package me.jishuna.jishlib.inventory;

import me.jishuna.jishlib.JishlibPlugin;
import me.jishuna.jishlib.event.JishlibDisableEvent;
import me.jishuna.jishlib.event.JishlibReloadEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InventoryManager implements Listener {
    private static InventoryManager INSTANCE;

    public static InventoryManager instance() {
        if (INSTANCE == null) {
            INSTANCE = new InventoryManager();
        }

        return INSTANCE;
    }

    private final Map<UUID, InventorySession> inventoryMap = new HashMap<>();

    private InventoryManager() {
        Bukkit.getPluginManager().registerEvents(this, JishlibPlugin.instance());
        Bukkit.getPluginManager().registerEvents(new CustomInventoryListener(this), JishlibPlugin.instance());
    }

    public InventorySession getSession(HumanEntity entity) {
        return getSession(entity.getUniqueId());
    }

    public InventorySession getSession(UUID id) {
        return inventoryMap.get(id);
    }

    public InventorySession openInventory(HumanEntity entity, CustomInventory inventory) {
        InventorySession session = new InventorySession((Player) entity, inventory);
        inventory.open(entity);

        inventoryMap.put(entity.getUniqueId(), session);
        return session;
    }

    public void discardSession(HumanEntity entity) {
        discardSession(entity.getUniqueId());
    }

    public void discardSession(UUID id) {
        inventoryMap.remove(id);
    }

    public void closeAll() {
        for (InventorySession session : inventoryMap.values()) {
            session.getPlayer().closeInventory();
        }

        inventoryMap.clear();
    }

    @EventHandler
    public void onReload(JishlibReloadEvent e) {
        cleanup();
    }

    @EventHandler
    public void onDisable(JishlibDisableEvent e) {
        cleanup();
    }

    private void cleanup() {
        closeAll();
        INSTANCE = null;
    }
}

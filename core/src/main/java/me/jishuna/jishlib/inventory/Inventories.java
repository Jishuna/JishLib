package me.jishuna.jishlib.inventory;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import me.jishuna.jishlib.Feature;
import me.jishuna.jishlib.Plugin;

public final class Inventories implements Feature {
    private static Inventories INSTANCE;

    public static InventorySession getSession(HumanEntity entity) {
        return getSession(entity.getUniqueId());
    }

    public static InventorySession getSession(UUID id) {
        return getInstance().inventoryMap.get(id);
    }

    public static InventorySession openInventory(HumanEntity entity, CustomInventory inventory) {
        InventorySession session = new InventorySession((Player) entity, inventory);
        inventory.open(entity);
        inventory.consumeOpenEvent(session);

        getInstance().inventoryMap.put(entity.getUniqueId(), session);
        return session;
    }

    public static void discardSession(HumanEntity entity) {
        discardSession(entity.getUniqueId());
    }

    public static void discardSession(UUID id) {
        getInstance().inventoryMap.remove(id);
    }

    public static void closeAll() {
        for (InventorySession session : getInstance().inventoryMap.values()) {
            session.getPlayer().closeInventory();
        }

        getInstance().inventoryMap.clear();
    }

    private static Inventories getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Inventories();
            Plugin.getInstance().registerFeature(INSTANCE);
        }

        return INSTANCE;
    }

    private final HashMap<UUID, InventorySession> inventoryMap = new HashMap<>();

    private Inventories() {
        CustomInventoryListener listener = new CustomInventoryListener();
        Bukkit.getPluginManager().registerEvents(listener, Plugin.getInstance());
    }

    @Override
    public void cleanup() {
        closeAll();
        INSTANCE = null;
    }
}

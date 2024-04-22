package me.jishuna.jishlib.inventory;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import me.jishuna.jishlib.Feature;
import me.jishuna.jishlib.SpigotPlugin;

public class Inventories implements Feature {
    private static Inventories INSTANCE;

    public static Inventories getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Inventories();
            SpigotPlugin.getInstance().getActiveFeatures().add(INSTANCE);
        }

        return INSTANCE;
    }

    private final HashMap<UUID, InventorySession> inventoryMap = new HashMap<>();

    public Inventories() {
        CustomInventoryListener listener = new CustomInventoryListener(this);
        Bukkit.getPluginManager().registerEvents(listener, SpigotPlugin.getInstance());
    }

    public InventorySession getSession(HumanEntity entity) {
        return getSession(entity.getUniqueId());
    }

    public InventorySession getSession(UUID id) {
        return this.inventoryMap.get(id);
    }

    public InventorySession openInventory(HumanEntity entity, CustomInventory<?> inventory) {
        InventorySession session = new InventorySession((Player) entity, inventory);
        inventory.open(entity);

        this.inventoryMap.put(entity.getUniqueId(), session);
        return session;
    }

    public void discardSession(HumanEntity entity) {
        discardSession(entity.getUniqueId());
    }

    public void discardSession(UUID id) {
        this.inventoryMap.remove(id);
    }

    public void closeAll() {
        for (InventorySession session : this.inventoryMap.values()) {
            session.getPlayer().closeInventory();
        }

        this.inventoryMap.clear();
    }

    @Override
    public void cleanup() {
        closeAll();
        INSTANCE = null;
    }
}

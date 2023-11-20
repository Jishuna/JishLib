package me.jishuna.jishlib.inventory;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import me.jishuna.jishlib.JishLib;

public final class InventoryAPI {
    private static InventoryManager manager;

    public static void initialize() {
        Preconditions.checkArgument(manager == null, "InventoryAPI already initialized!");
        Preconditions.checkArgument(JishLib.isInitialized(), "JishLib must be initialized first!");

        manager = new InventoryManager();
        Bukkit.getPluginManager().registerEvents(new CustomInventoryListener(), JishLib.getPlugin());
    }

    public static void destroySession(HumanEntity player) {
        destroySession(player.getUniqueId());
    }

    public static void destroySession(UUID id) {
        InventorySession session = getInstance().inventoryMap.remove(id);
        if (session != null) {
            session.onDiscard();
        }
    }

    public static InventorySession getSession(HumanEntity player) {
        return getSession(player.getUniqueId());
    }

    public static InventorySession getSession(UUID id) {
        return getInstance().inventoryMap.get(id);
    }

    public static void openInventory(HumanEntity player, CustomInventory<?> inventory, boolean recordHistory) {
        getInstance().inventoryMap
                .computeIfAbsent(player.getUniqueId(), k -> new InventorySession(player, inventory))
                .open(inventory, recordHistory);
    }

    public static void openInventory(HumanEntity player, CustomInventory<?> inventory, InventorySession initialSession) {
        if (initialSession == null) {
            openInventory(player, inventory, true);
            return;
        }

        getInstance().inventoryMap.put(player.getUniqueId(), initialSession);
        initialSession.changeTo(inventory, false);
    }

    private static InventoryManager getInstance() {
        if (manager == null) {
            throw new IllegalStateException("InventoryAPI not initialized!");
        }
        return manager;
    }

    private InventoryAPI() {
    }

    static final class InventoryManager {
        private final HashMap<UUID, InventorySession> inventoryMap = new HashMap<>();
    }
}

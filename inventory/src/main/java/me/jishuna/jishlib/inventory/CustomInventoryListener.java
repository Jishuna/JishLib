package me.jishuna.jishlib.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import me.jishuna.jishlib.inventory.InventorySession.State;

public class CustomInventoryListener implements Listener {
    private final InventoryManager manager;

    public CustomInventoryListener(InventoryManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        InventorySession session = manager.getSession(event.getWhoClicked());

        if (session != null) {
            ClickContext context = new ClickContext(event, session);
            session.getActive().consumeClickEvent(context);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event) {
        InventorySession session = manager.getSession(event.getPlayer());

        if (session != null) {
            session.getActive().consumeCloseEvent(session);

            if (session.getState() == State.NORMAL) {
                manager.discardSession(event.getPlayer());
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        InventorySession session = manager.getSession(player);

        if (session != null) {
            player.closeInventory();
            manager.discardSession(player);
        }
    }
}

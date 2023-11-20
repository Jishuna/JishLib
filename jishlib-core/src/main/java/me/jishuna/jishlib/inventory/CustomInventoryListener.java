package me.jishuna.jishlib.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import me.jishuna.jishlib.inventory.InventorySession.State;

public class CustomInventoryListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {
            return;
        }

        InventorySession session = InventoryAPI.getSession(event.getWhoClicked());

        if (session != null) {
            session.getActive().consumeClickEvent(event, session);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event) {
        InventorySession session = InventoryAPI.getSession(event.getPlayer());

        if (session != null) {
            session.getActive().consumeCloseEvent(event, session);

            if (session.getState() == State.NORMAL) {
                InventoryAPI.destroySession(event.getPlayer());
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLeave(PlayerQuitEvent event) {
        InventoryAPI.destroySession(event.getPlayer());
    }
}

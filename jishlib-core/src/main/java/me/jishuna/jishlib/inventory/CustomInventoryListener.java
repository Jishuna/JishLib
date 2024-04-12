package me.jishuna.jishlib.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import me.jishuna.jishlib.inventory.InventorySession.State;

public class CustomInventoryListener implements Listener {

    private final InventorySystem system;

    public CustomInventoryListener(InventorySystem system) {
        this.system = system;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        InventorySession session = this.system.getSession(event.getWhoClicked());

        if (session != null) {
            session.getActive().consumeClickEvent(event, session);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryOpen(InventoryOpenEvent event) {
        InventorySession session = this.system.getSession(event.getPlayer());

        if (session != null) {
            session.getActive().consumeOpenEvent(event, session);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event) {
        InventorySession session = this.system.getSession(event.getPlayer());

        if (session != null) {
            session.getActive().consumeCloseEvent(event, session);

            if (session.getState() == State.NORMAL) {
                this.system.discardSession(event.getPlayer());
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        InventorySession session = this.system.getSession(player);

        if (session != null) {
            player.closeInventory();
            this.system.discardSession(player);
        }
    }
}

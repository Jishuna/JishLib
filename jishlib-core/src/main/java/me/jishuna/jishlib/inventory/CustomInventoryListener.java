package me.jishuna.jishlib.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import me.jishuna.jishlib.inventory.CustomInventorySession.State;

public class CustomInventoryListener implements Listener {
    private final CustomInventoryManager manager;

    public CustomInventoryListener(CustomInventoryManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {
            return;
        }

        CustomInventorySession session = this.manager.getSession(event.getWhoClicked());

        if (session != null) {
            session.getActive().consumeClickEvent(event, session);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event) {
        CustomInventorySession session = this.manager.getSession(event.getPlayer());

        if (session != null) {
            session.getActive().consumeCloseEvent(event, session);

            if (session.getState() == State.NORMAL) {
                this.manager.destroySession(event.getPlayer());
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onvInventoryOpen(InventoryOpenEvent event) {
        CustomInventorySession session = this.manager.getSession(event.getPlayer());

        if (session != null) {
            session.getActive().consumeOpenEvent(event, session);
        }
    }
}

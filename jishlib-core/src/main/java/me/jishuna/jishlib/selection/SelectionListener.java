package me.jishuna.jishlib.selection;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class SelectionListener implements Listener {
    private final PlayerSelections instance;

    public SelectionListener(PlayerSelections instance) {
        this.instance = instance;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        this.instance.clearSelection(event.getPlayer());
    }
}

package me.jishuna.jishlib.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;

public record ClickContext(InventoryClickEvent event, InventorySession session) {

}
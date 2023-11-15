package me.jishuna.jishlib.nms.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public interface CustomAnvilMenu {
    public void setCallbackTarget(AnvilGuiInventory target);

    public Inventory getInventory();

    public InventoryView getView();

    public void update();
}

package me.jishuna.jishlib.util;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtils {

    private InventoryUtils() {
    }

    public static int getTotalAmount(Inventory inventory, ItemStack target) {
        int amount = 0;

        for (ItemStack item : inventory) {
            if (item == null) {
                continue;
            }

            if (item.isSimilar(target)) {
                amount += item.getAmount();
            }
        }
        return amount;
    }
}

package me.jishuna.jishlib.util;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Utility methods for {@link Inventory}.
 */
public class InventoryUtils {
    /**
     * Gets the total amount of the provided itemstack in the provided inventory.
     *
     * @param inventory the inventory to search
     * @param target    the item to look for
     * @return the total number of matching items
     */
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

    private InventoryUtils() {
    }
}

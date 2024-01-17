package me.jishuna.jishlib.util;

import com.google.common.base.Supplier;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

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

    public static void addOrDropItem(Inventory inventory, Supplier<Location> supplier, ItemStack... items) {
        Map<Integer, ItemStack> failed = inventory.addItem(items);
        if (failed.isEmpty()) {
            return;
        }

        Location location = supplier.get();
        World world = location.getWorld();
        for (ItemStack item : failed.values()) {
            world.dropItem(location, item, e -> {
                e.setVelocity(new Vector());
                e.setPickupDelay(0);
            });
        }
    }

    private InventoryUtils() {
    }
}

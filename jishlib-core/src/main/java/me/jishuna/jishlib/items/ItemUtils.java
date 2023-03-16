package me.jishuna.jishlib.items;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class ItemUtils {
	private ItemUtils() {
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

	public static <T> T getPersistantData(ItemStack item, NamespacedKey key, PersistentDataType<T, T> type) {
		if (item == null || !item.hasItemMeta()) {
			return null;
		}

		return item.getItemMeta().getPersistentDataContainer().get(key, type);
	}
}

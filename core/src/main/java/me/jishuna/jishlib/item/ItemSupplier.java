package me.jishuna.jishlib.item;

import java.util.function.Supplier;
import org.bukkit.inventory.ItemStack;

@FunctionalInterface
public interface ItemSupplier extends Supplier<ItemStack> {
}

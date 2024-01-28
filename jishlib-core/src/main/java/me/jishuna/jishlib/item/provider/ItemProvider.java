package me.jishuna.jishlib.item.provider;

import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.item.ItemstackRepresentable;

public interface ItemProvider extends ItemstackRepresentable {
    public ItemStack get();

    @Override
    default ItemStack asItemStack() {
        return get();
    }
}

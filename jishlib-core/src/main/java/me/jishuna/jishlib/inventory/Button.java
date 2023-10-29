package me.jishuna.jishlib.inventory;

import java.util.function.BiConsumer;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.jishuna.jishlib.item.provider.FixedItemProvider;
import me.jishuna.jishlib.item.provider.ItemProvider;

public class Button {
    private final ItemProvider provider;
    private final BiConsumer<InventoryClickEvent, CustomInventory<?>> action;

    public Button(ItemProvider provider, BiConsumer<InventoryClickEvent, CustomInventory<?>> action) {
        this.provider = provider;
        this.action = action;
    }

    public Button(ItemStack item, BiConsumer<InventoryClickEvent, CustomInventory<?>> action) {
        this(FixedItemProvider.create(item), action);
    }

    public ItemProvider getProvider() {
        return provider;
    }

    public BiConsumer<InventoryClickEvent, CustomInventory<?>> getAction() {
        return action;
    }
}

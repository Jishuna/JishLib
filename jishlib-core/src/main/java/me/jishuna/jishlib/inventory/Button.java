package me.jishuna.jishlib.inventory;

import java.util.function.BiConsumer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.item.provider.FixedItemProvider;
import me.jishuna.jishlib.item.provider.ItemProvider;

public class Button {
    private final BiConsumer<InventoryClickEvent, CustomInventorySession> action;
    private final ItemProvider provider;

    public Button(ItemProvider provider, BiConsumer<InventoryClickEvent, CustomInventorySession> action) {
        this.provider = provider;
        this.action = action;
    }

    public Button(ItemStack item, BiConsumer<InventoryClickEvent, CustomInventorySession> action) {
        this(FixedItemProvider.create(item), action);
    }

    public BiConsumer<InventoryClickEvent, CustomInventorySession> getAction() {
        return this.action;
    }

    public ItemProvider getProvider() {
        return this.provider;
    }
}

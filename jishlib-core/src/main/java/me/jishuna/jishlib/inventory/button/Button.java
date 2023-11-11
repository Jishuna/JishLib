package me.jishuna.jishlib.inventory.button;

import java.util.function.BiConsumer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.inventory.session.InventorySession;
import me.jishuna.jishlib.item.provider.FixedItemProvider;
import me.jishuna.jishlib.item.provider.ItemProvider;

public class Button {
    private final BiConsumer<InventoryClickEvent, InventorySession> action;
    private final ItemProvider provider;

    public Button(ItemProvider provider, BiConsumer<InventoryClickEvent, InventorySession> action) {
        this.provider = provider;
        this.action = action;
    }

    public Button(ItemStack item, BiConsumer<InventoryClickEvent, InventorySession> action) {
        this(FixedItemProvider.create(item), action);
    }

    public BiConsumer<InventoryClickEvent, InventorySession> getAction() {
        return this.action;
    }

    public ItemProvider getProvider() {
        return this.provider;
    }
}

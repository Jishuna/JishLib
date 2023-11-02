package me.jishuna.jishlib.item.provider;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.item.ItemBuilder;

public class FixedItemProvider implements ItemProvider {
    private final ItemStack item;

    private FixedItemProvider(ItemStack item) {
        this.item = item;
    }

    public static FixedItemProvider create(ItemBuilder builder) {
        return create(builder.build());
    }

    public static FixedItemProvider create(ItemStack item) {
        return new FixedItemProvider(item);
    }

    public static FixedItemProvider create(Material material) {
        return create(new ItemStack(material));
    }

    @Override
    public ItemStack get() {
        return this.item;
    }
}

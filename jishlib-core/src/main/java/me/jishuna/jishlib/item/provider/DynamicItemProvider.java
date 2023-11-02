package me.jishuna.jishlib.item.provider;

import java.util.function.Supplier;
import org.bukkit.inventory.ItemStack;

public class DynamicItemProvider implements ItemProvider {
    private final Supplier<ItemStack> supplier;

    private DynamicItemProvider(Supplier<ItemStack> supplier) {
        this.supplier = supplier;
    }

    public static DynamicItemProvider create(Supplier<ItemStack> supplier) {
        return new DynamicItemProvider(supplier);
    }

    @Override
    public ItemStack get() {
        return this.supplier.get();
    }
}

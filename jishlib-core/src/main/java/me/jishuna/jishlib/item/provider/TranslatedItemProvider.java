package me.jishuna.jishlib.item.provider;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.item.ItemBuilder;
import me.jishuna.jishlib.message.Messages;

public class TranslatedItemProvider implements ItemProvider {
    private final ItemBuilder builder;
    private final String loreKey;
    private final String nameKey;

    private TranslatedItemProvider(ItemBuilder builder, String nameKey, String loreKey) {
        this.builder = builder;
        this.nameKey = nameKey;
        this.loreKey = loreKey;
    }

    public static TranslatedItemProvider create(ItemBuilder builder, String nameKey, String loreKey) {
        return new TranslatedItemProvider(builder, nameKey, loreKey);
    }

    public static TranslatedItemProvider create(Material material, String nameKey, String loreKey) {
        return create(ItemBuilder.create(material), nameKey, loreKey);
    }

    @Override
    public ItemStack get() {
        ItemBuilder copy = this.builder.clone();
        copy.name(Messages.get(this.nameKey));
        copy.lore(Messages.getList(this.loreKey));
        return copy.build();
    }
}

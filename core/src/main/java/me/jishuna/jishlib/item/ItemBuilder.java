package me.jishuna.jishlib.item;

import me.jishuna.jishlib.ComponentSerializers;
import me.jishuna.jishlib.adapter.Adapter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;
import java.util.Set;

public class ItemBuilder {
    private final ItemStack item;
    private final ItemMeta meta;
    private Component name;
    private final Collection<Component> lore;

    private ItemBuilder(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
        this.name = Adapter.get().getName(this.meta);
        this.lore = Adapter.get().getLore(this.meta);
    }

    public static ItemBuilder of(Material material) {
        return new ItemBuilder(new ItemStack(material));
    }

    public static ItemBuilder of(Material material, int amount) {
        return new ItemBuilder(new ItemStack(material, amount));
    }

    public static ItemBuilder of(ItemStack item) {
        return new ItemBuilder(item);
    }

    public Component name() {
        return name;
    }

    public ItemBuilder name(Component name) {
        this.name = name;
        return this;
    }

    public ItemBuilder name(String name, TagResolver... resolvers) {
        this.name = ComponentSerializers.MINI_MESSAGE.deserialize(name, resolvers);
        return this;
    }

    public Collection<Component> lore() {
        return lore;
    }

    public ItemBuilder lore(Component line) {
        lore.add(line);
        return this;
    }

    public ItemBuilder lore(String line, TagResolver... resolvers) {
        lore.add(ComponentSerializers.MINI_MESSAGE.deserialize(line, resolvers));
        return this;
    }

    public Set<ItemFlag> flags() {
        return meta.getItemFlags();
    }

    public ItemBuilder flags(ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }

    public Boolean enchantmentGlint() {
        return meta.getEnchantmentGlintOverride();
    }

    public ItemBuilder enchantmentGlint(boolean glint) {
        meta.setEnchantmentGlintOverride(glint);
        return this;
    }

    public boolean tooltip() {
        return meta.isHideTooltip();
    }

    public ItemBuilder tooltip(boolean tooltip) {
        meta.setHideTooltip(tooltip);
        return this;
    }

    public ItemBuilder noTooltip() {
        meta.setHideTooltip(true);
        return this;
    }

    public NamespacedKey model() {
        return meta.getItemModel();
    }

    public ItemBuilder model(NamespacedKey key) {
        meta.setItemModel(key);
        return this;
    }

    public ItemStack build() {
        Adapter.get().setName(meta, name);
        Adapter.get().setLore(meta, lore);

        item.setItemMeta(meta);
        return item;
    }
}

package me.jishuna.jishlib.itembuilder;

import me.jishuna.jishlib.ComponentSerializers;
import me.jishuna.jishlib.JishLib;
import me.jishuna.jishlib.adapter.Adapter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;

public class ItemBuilder {
    private final ItemStack item;
    private final ItemMeta meta;
    private Component name;
    private Component displayName;
    private final Collection<Component> lore;

    private ItemBuilder(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
        this.name = JishLib.adapter().getName(this.meta);
        this.displayName = JishLib.adapter().getDisplayName(this.meta);
        this.lore = JishLib.adapter().getLore(this.meta);
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

    public Component displayName() {
        return displayName;
    }

    public ItemBuilder displayName(Component name) {
        this.displayName = name;
        return this;
    }

    public ItemBuilder displayName(String name, TagResolver... resolvers) {
        this.displayName = ComponentSerializers.MINI_MESSAGE.deserialize(name, resolvers);
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

    public boolean tooltip() {
        return meta.isHideTooltip();
    }

    public ItemBuilder tooltip(boolean tooltip) {
        meta.setHideTooltip(tooltip);
        return this;
    }

    public ItemStack build() {
        Adapter adapter = JishLib.adapter();
        adapter.setName(meta, name);
        adapter.setDisplayName(meta, name);
        adapter.setLore(meta, lore);

        item.setItemMeta(meta);
        return item;
    }
}

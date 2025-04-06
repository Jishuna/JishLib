package me.jishuna.jishlib.adapter;

import me.jishuna.jishlib.ComponentSerializers;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BukkitFallbackAdapter implements Adapter {

    @Override
    public Collection<Component> getLore(ItemMeta meta) {
        List<Component> lore = new ArrayList<>();

        if (meta == null || meta.getLore() == null) {
            return lore;
        }

        for (String line : meta.getLore()) {
            lore.add(ComponentSerializers.LEGACY_SERIALIZER.deserialize(line));
        }

        return lore;
    }

    @Override
    public void setLore(ItemMeta meta, Collection<Component> lore) {
        if (meta == null || lore == null) {
            return;
        }

        List<String> loreStrings = new ArrayList<>();
        for (Component component : lore) {
            loreStrings.add(ComponentSerializers.LEGACY_SERIALIZER.serialize(component));
        }

        meta.setLore(loreStrings);
    }

    @Override
    public Component getName(ItemMeta meta) {
        if (meta == null || !meta.hasItemName()) {
            return null;
        }

        return ComponentSerializers.LEGACY_SERIALIZER.deserialize(meta.getItemName());
    }

    @Override
    public void setName(ItemMeta meta, Component name) {
        if (meta == null || name == null) {
            return;
        }

        meta.setItemName(ComponentSerializers.LEGACY_SERIALIZER.serialize(name));
    }

    @Override
    public Component getDisplayName(ItemMeta meta) {
        if (meta == null || !meta.hasDisplayName()) {
            return null;
        }

        return ComponentSerializers.LEGACY_SERIALIZER.deserialize(meta.getDisplayName());
    }

    @Override
    public void setDisplayName(ItemMeta meta, Component name) {
        if (meta == null || name == null) {
            return;
        }

        meta.setDisplayName(ComponentSerializers.LEGACY_SERIALIZER.serialize(name));
    }
}

package me.jishuna.jishlib.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.meta.ItemMeta;
import me.jishuna.jishlib.Constants;
import me.jishuna.jishlib.nms.NMS;

public class ItemLore {
    public static ItemLore fromMeta(ItemMeta meta) {
        if (!meta.hasLore()) {
            return new ItemLore(Collections.emptyList());
        }

        if (NMS.isInitialized()) {
            return new ItemLore(NMS.get().getItemLoreComponents(meta));
        }

        List<Component> itemLore = new ArrayList<>();
        for (String line : meta.getLore()) {
            itemLore.add(Constants.LEGACY_SERIALIZER.deserialize(line));
        }

        return new ItemLore(itemLore);
    }

    private final List<Component> lines;

    private ItemLore(List<Component> lore) {
        this.lines = new ArrayList<>(lore);
    }

    public void apply(ItemMeta meta) {
        if (NMS.isInitialized()) {
            NMS.get().setItemLoreComponents(meta, this.lines);
        } else {
            meta.setLore(getStrings());
        }
    }

    public void add(Component... lore) {
        Collections.addAll(this.lines, lore);
    }

    public void add(String... lore) {
        for (String line : lore) {
            this.lines.add(Constants.LEGACY_SERIALIZER.deserialize(line));
        }
    }

    public void removeIf(Predicate<? super Component> predicate) {
        this.lines.removeIf(predicate);
    }

    public List<Component> getComponents() {
        return Collections.unmodifiableList(this.lines);
    }

    public List<String> getStrings() {
        List<String> lore = new ArrayList<>();
        this.lines.forEach(c -> lore.add(Constants.LEGACY_SERIALIZER.serialize(c)));

        return lore;
    }
}

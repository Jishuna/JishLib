package me.jishuna.jishlib.data.adapter;

import static me.jishuna.jishlib.data.adapter.DefaultAdapters.COMPONENT;
import static me.jishuna.jishlib.data.adapter.DefaultAdapters.MATERIAL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.data.DataType;
import me.jishuna.jishlib.data.holder.DataHolder;
import me.jishuna.jishlib.data.holder.StringDataHolder;
import me.jishuna.jishlib.data.holder.collection.ListDataHolder;
import me.jishuna.jishlib.data.holder.collection.MapDataHolder;
import me.jishuna.jishlib.data.holder.number.NumericDataHolder;
import me.jishuna.jishlib.item.ItemBuilder;

public class ItemStackAdapter implements TypeAdapter<MapDataHolder, ItemStack> {
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static final CollectionAdapter<Component> COMPONENT_LIST = (CollectionAdapter<Component>) TypeAdapterRegistry.getAdapter(new DataType(List.class, List.of(new DataType<>(Component.class))));

    @Override
    public Class<MapDataHolder> getObjectType() {
        return MapDataHolder.class;
    }

    @Override
    public ItemStack deserialize(MapDataHolder data) {
        ItemBuilder builder = ItemBuilder.of(MATERIAL.deserialize(data.get("material", StringDataHolder.class)));

        builder.amount(data.get("amount", Integer.class, 1));
        data.find("name", String.class).ifPresent(s -> builder.name(COMPONENT.fromString(s)));
        builder.lore(COMPONENT_LIST.deserialize((ListDataHolder) data.get("lore")).toArray(Component[]::new));

        return builder.build();
    }

    @Override
    public MapDataHolder serialize(ItemStack value) {
        ItemBuilder builder = ItemBuilder.of(value);

        Map<String, DataHolder<?>> dataMap = new LinkedHashMap<>();
        dataMap.put("material", MATERIAL.serialize(value.getType()));
        dataMap.put("amount", NumericDataHolder.of(builder.amount()));
        if (builder.hasName()) {
            dataMap.put("name", COMPONENT.serialize(builder.name()));
        }
        dataMap.put("lore", COMPONENT_LIST.serialize(builder.lore().getComponents()));

        return MapDataHolder.of(dataMap);
    }

    @Override
    public ItemStack fromString(String value) {
        return null;
    }

    @Override
    public String toString(ItemStack value) {
        return null;
    }
}

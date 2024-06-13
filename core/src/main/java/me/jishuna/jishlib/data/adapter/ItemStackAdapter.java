package me.jishuna.jishlib.data.adapter;

import static me.jishuna.jishlib.data.adapter.DefaultAdapters.COMPONENT;
import static me.jishuna.jishlib.data.adapter.DefaultAdapters.MATERIAL;
import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.Constants;
import me.jishuna.jishlib.data.holder.DataHolder;
import me.jishuna.jishlib.data.holder.StringDataHolder;
import me.jishuna.jishlib.data.holder.collection.MapDataHolder;
import me.jishuna.jishlib.data.holder.number.NumericDataHolder;
import me.jishuna.jishlib.item.ItemBuilder;

public class ItemStackAdapter implements TypeAdapter<MapDataHolder, ItemStack> {
    @Override
    public Class<MapDataHolder> getObjectType() {
        return MapDataHolder.class;
    }

    @Override
    public ItemStack deserialize(MapDataHolder data) {
        ItemBuilder builder = ItemBuilder.of(MATERIAL.deserialize(data.get("material", StringDataHolder.class)));
        builder.amount(data.get("amount", NumericDataHolder.class).intValue());
        builder.name(COMPONENT.deserialize(data.get("name", StringDataHolder.class)));

        return builder.build();
    }

    @Override
    public MapDataHolder serialize(ItemStack value) {
        ItemBuilder builder = ItemBuilder.of(value);

        Map<String, DataHolder<?>> dataMap = new LinkedHashMap<>();
        dataMap.put("material", MATERIAL.serialize(value.getType()));
        dataMap.put("amount", NumericDataHolder.of(builder.amount()));
        dataMap.put("name", COMPONENT.serialize(Constants.LEGACY_SERIALIZER.deserializeOrNull(builder.name())));

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

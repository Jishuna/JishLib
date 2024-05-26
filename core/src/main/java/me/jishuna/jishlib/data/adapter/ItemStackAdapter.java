package me.jishuna.jishlib.data.adapter;

import static me.jishuna.jishlib.data.adapter.TypeAdapters.COMPONENT;
import static me.jishuna.jishlib.data.adapter.TypeAdapters.MATERIAL;
import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.inventory.ItemStack;
import me.jishuna.jishlib.Constants;
import me.jishuna.jishlib.data.object.DataObject;
import me.jishuna.jishlib.data.object.MapDataObject;
import me.jishuna.jishlib.data.object.PrimitiveDataObject;
import me.jishuna.jishlib.item.ItemBuilder;

public class ItemStackAdapter implements TypeAdapter<MapDataObject, ItemStack> {
    @Override
    public Class<MapDataObject> getObjectType() {
        return MapDataObject.class;
    }

    @Override
    public ItemStack deserialize(MapDataObject data) {
        ItemBuilder builder = ItemBuilder.of(MATERIAL.deserialize(data.getPrimitive("material")));
        builder.amount(data.getPrimitive("amount").asInt());
        builder.name(COMPONENT.deserialize(data.getPrimitive("name")));

        return builder.build();
    }

    @Override
    public MapDataObject serialize(ItemStack value) {
        ItemBuilder builder = ItemBuilder.of(value);

        Map<String, DataObject<?>> dataMap = new LinkedHashMap<>();
        dataMap.put("material", MATERIAL.serialize(value.getType()));
        dataMap.put("amount", PrimitiveDataObject.of(builder.amount()));
        dataMap.put("name", COMPONENT.serialize(Constants.LEGACY_SERIALIZER.deserializeOrNull(builder.name())));

        return MapDataObject.of(dataMap);
    }

}

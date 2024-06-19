package me.jishuna.jishlib.data.adapter;

import java.util.LinkedHashMap;
import java.util.Map;
import me.jishuna.jishlib.data.DataType;
import me.jishuna.jishlib.data.holder.DataHolder;
import me.jishuna.jishlib.data.holder.collection.MapDataHolder;

public class MapAdapter<K, V> implements TypeAdapter<MapDataHolder, Map<K, V>> {
    private final TypeAdapter<DataHolder<?>, K> keyAdapter;
    private final TypeAdapter<DataHolder<?>, V> valueAdapter;

    @SuppressWarnings("unchecked")
    public MapAdapter(DataType<?> type) {
        this.keyAdapter = (TypeAdapter<DataHolder<?>, K>) TypeAdapterRegistry.getAdapter(type.getComponentTypes().get(0));
        this.valueAdapter = (TypeAdapter<DataHolder<?>, V>) TypeAdapterRegistry.getAdapter(type.getComponentTypes().get(1));
    }

    @Override
    public Class<MapDataHolder> getObjectType() {
        return MapDataHolder.class;
    }

    @Override
    public Map<K, V> deserialize(MapDataHolder data) {
        Map<K, V> map = new LinkedHashMap<>();

        data.forEach((k, v) -> {
            K mapKey = this.keyAdapter.fromString(k);
            V mapValue = this.valueAdapter.deserialize(v);

            map.put(mapKey, mapValue);
        });

        return map;
    }

    @Override
    public MapDataHolder serialize(Map<K, V> value) {
        Map<String, DataHolder<?>> dataMap = new LinkedHashMap<>();

        value.forEach((k, v) -> {
            String key = this.keyAdapter.toString(k);
            DataHolder<?> holder = this.valueAdapter.serialize(v);
            if (key != null && holder != null) {
                holder.setName(key);
                dataMap.put(key, holder);
            }
        });

        return MapDataHolder.of(dataMap);
    }

    @Override
    public Map<K, V> fromString(String value) {
        return null;
    }

    @Override
    public String toString(Map<K, V> value) {
        return null;
    }
}

package me.jishuna.jishlib.data.adapter;

import java.util.LinkedHashMap;
import java.util.Map;
import me.jishuna.jishlib.data.DataType;
import me.jishuna.jishlib.data.object.DataObject;
import me.jishuna.jishlib.data.object.MapDataObject;

public class MapAdapter<K, V> implements TypeAdapter<MapDataObject, Map<K, V>> {
    private final TypeAdapter<DataObject<?>, K> keyAdapter;
    private final TypeAdapter<DataObject<?>, V> valueAdapter;

    @SuppressWarnings("unchecked")
    public MapAdapter(DataType<?> type) {
        this.keyAdapter = (TypeAdapter<DataObject<?>, K>) TypeAdapterRegistry.getAdapter(type.getComponentTypes().get(0));
        this.valueAdapter = (TypeAdapter<DataObject<?>, V>) TypeAdapterRegistry.getAdapter(type.getComponentTypes().get(1));
    }

    @Override
    public Class<MapDataObject> getObjectType() {
        return MapDataObject.class;
    }

    @Override
    public Map<K, V> deserialize(MapDataObject data) {
        Map<K, V> map = new LinkedHashMap<>();

        data.forEach((k, v) -> {
            K mapKey = this.keyAdapter.fromString(k);
            V mapValue = this.valueAdapter.deserialize(v);

            map.put(mapKey, mapValue);
        });

        return map;
    }

    @Override
    public MapDataObject serialize(Map<K, V> value) {
        Map<String, DataObject<?>> dataMap = new LinkedHashMap<>();

        value.forEach((k, v) -> {
            dataMap.put(this.keyAdapter.toString(k), this.valueAdapter.serialize(v));
        });

        return MapDataObject.of(dataMap);
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

package me.jishuna.jishlib.config.adapter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import me.jishuna.jishlib.config.ConfigApi;
import me.jishuna.jishlib.config.ConfigType;

public class MapAdapter<K, V> implements TypeAdapter<Map<String, Object>, Map<K, V>> {
    private final TypeAdapter<Object, V> valueAdapter;
    private final TypeAdapterString<Object, K> keyAdapter;

    @SuppressWarnings("unchecked")
    public MapAdapter(ConfigType<?> type) {
        this.keyAdapter = (TypeAdapterString<Object, K>) ConfigApi.getStringAdapter(type.getComponentTypes().get(0));
        this.valueAdapter = (TypeAdapter<Object, V>) ConfigApi.getAdapter(type.getComponentTypes().get(1));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<Map<String, Object>> getSavedType() {
        return (Class<Map<String, Object>>) (Object) Map.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<Map<K, V>> getRuntimeType() {
        return (Class<Map<K, V>>) (Object) Map.class;
    }

    @Override
    public Map<K, V> read(Map<String, Object> value) {
        Map<K, V> map = new LinkedHashMap<>();

        for (Entry<String, Object> entry : value.entrySet()) {
            K mapKey = this.keyAdapter.fromString(entry.getKey());
            V mapValue = this.valueAdapter.read(entry.getValue());

            map.put(mapKey, mapValue);
        }

        return map;
    }

    @Override
    public Map<String, Object> write(Map<K, V> value, Map<String, Object> existing, boolean replace) {
        if (existing == null) {
            existing = new LinkedHashMap<>();
        }

        for (Entry<K, V> entry : value.entrySet()) {
            String key = this.keyAdapter.toString(entry.getKey());

            if (!existing.containsKey(key) || replace) {
                Object saveValue = this.valueAdapter.write(entry.getValue(), existing.get(key), replace);
                if (saveValue != null) {
                    existing.put(key, saveValue);
                }
            }
        }
        return existing;
    }
}

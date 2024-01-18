package me.jishuna.jishlib.config.adapter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.configuration.ConfigurationSection;
import me.jishuna.jishlib.config.ConfigAPI;
import me.jishuna.jishlib.config.ConfigType;
import me.jishuna.jishlib.config.CustomConfig;

public class MapAdapter<K, V> implements TypeAdapter<ConfigurationSection, Map<K, V>> {
    private final TypeAdapter<Object, V> valueAdapter;
    private final TypeAdapterString<Object, K> keyAdapter;

    @SuppressWarnings("unchecked")
    public MapAdapter(ConfigType<?> type) {
        this.keyAdapter = (TypeAdapterString<Object, K>) ConfigAPI.getStringAdapter(type.getComponentTypes().get(0));
        this.valueAdapter = (TypeAdapter<Object, V>) ConfigAPI.getAdapter(type.getComponentTypes().get(1));
    }

    @Override
    public Class<ConfigurationSection> getSavedType() {
        return ConfigurationSection.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<Map<K, V>> getRuntimeType() {
        return (Class<Map<K, V>>) (Object) Map.class;
    }

    @Override
    public Map<K, V> read(ConfigurationSection section) {
        Map<K, V> map = new LinkedHashMap<>();

        for (Entry<String, Object> entry : section.getValues(false).entrySet()) {
            K mapKey = this.keyAdapter.fromString(entry.getKey());
            V mapValue = this.valueAdapter.read(entry.getValue());

            map.put(mapKey, mapValue);
        }

        return map;
    }

    @Override
    public ConfigurationSection write(Map<K, V> value, ConfigurationSection existing, boolean replace) {
        if (existing == null) {
            existing = new CustomConfig();
        }

        for (Entry<K, V> entry : value.entrySet()) {
            String key = this.keyAdapter.toString(entry.getKey());

            if (!existing.isSet(key) || replace) {
                Object saveValue = this.valueAdapter.write(entry.getValue(), existing.get(key), replace);
                if (saveValue != null) {
                    existing.set(key, saveValue);
                }
            }
        }
        return existing;
    }
}

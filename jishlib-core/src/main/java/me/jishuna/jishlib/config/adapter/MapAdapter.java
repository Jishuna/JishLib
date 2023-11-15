package me.jishuna.jishlib.config.adapter;

import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.configuration.ConfigurationSection;
import me.jishuna.jishlib.config.ConfigApi;
import me.jishuna.jishlib.config.ConfigType;

public class MapAdapter<K, V, T extends Map<K, V>> implements TypeAdapter<T> {
    private final TypeAdapter<V> valueAdapter;
    private final StringAdapter<K> keyAdapter;

    @SuppressWarnings("unchecked")
    public MapAdapter(ConfigType<?> type) {
        this.keyAdapter = (StringAdapter<K>) ConfigApi.getStringAdapter(type.getComponentTypes().get(0));
        this.valueAdapter = (TypeAdapter<V>) ConfigApi.getAdapter(type.getComponentTypes().get(1));
    }

    @Override
    @SuppressWarnings("unchecked")
    public T read(ConfigurationSection config, String path) {
        T map = (T) new LinkedHashMap<>();

        if (!config.isConfigurationSection(path)) {
            return map;
        }

        ConfigurationSection section = config.getConfigurationSection(path);
        boolean isString = (this.keyAdapter instanceof StringTypeAdapter);

        for (String key : section.getKeys(isString)) {
            if (isString && section.isConfigurationSection(key)) {
                continue;
            }

            K mapKey = this.keyAdapter.fromString(key);
            V mapValue = this.valueAdapter.read(section, key);
            map.put(mapKey, mapValue);

        }

        return map;
    }

    @Override
    public void write(ConfigurationSection config, String path, T value, boolean replace) {
        value.forEach((k, v) -> {
            String newPath = new StringBuilder(path).append(".").append(this.keyAdapter.toString(k)).toString();
            if (!replace && config.isSet(newPath)) {
                return;
            }

            this.valueAdapter.write(config, newPath, v, replace);
        });
    }
}

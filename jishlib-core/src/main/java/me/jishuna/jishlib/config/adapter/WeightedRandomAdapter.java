package me.jishuna.jishlib.config.adapter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import me.jishuna.jishlib.config.ConfigAPI;
import me.jishuna.jishlib.config.ConfigType;
import me.jishuna.jishlib.datastructure.WeightedRandom;

public class WeightedRandomAdapter<R> implements TypeAdapter<Map<String, Object>, WeightedRandom<R>> {

    private final TypeAdapterString<Object, R> adapter;

    @SuppressWarnings("unchecked")
    public WeightedRandomAdapter(ConfigType<?> type) {
        this.adapter = (TypeAdapterString<Object, R>) ConfigAPI.getStringAdapter(type.getComponentTypes().get(0));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<Map<String, Object>> getSavedType() {
        return (Class<Map<String, Object>>) (Object) Map.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<WeightedRandom<R>> getRuntimeType() {
        return (Class<WeightedRandom<R>>) (Object) WeightedRandom.class;
    }

    @Override
    public WeightedRandom<R> read(Map<String, Object> map) {
        WeightedRandom<R> random = new WeightedRandom<>();

        for (Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (!(value instanceof Number number)) {
                continue;
            }

            double weight = number.doubleValue();
            R val = this.adapter.fromString(entry.getKey());

            random.add(weight, val);
        }
        return random;
    }

    @Override
    public Map<String, Object> write(WeightedRandom<R> value, Map<String, Object> existing, boolean replace) {
        if (existing == null) {
            existing = new LinkedHashMap<>();
        }

        double lastKey = 0;
        for (Entry<Double, R> entry : value.getEntries()) {
            String key = this.adapter.toString(entry.getValue());

            if (!existing.containsKey(key) || replace) {
                existing.put(key, entry.getKey() - lastKey);
                lastKey = entry.getKey();
            }
        }

        return existing;
    }
}

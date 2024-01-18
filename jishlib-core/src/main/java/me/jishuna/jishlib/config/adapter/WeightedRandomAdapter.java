package me.jishuna.jishlib.config.adapter;

import java.util.Map.Entry;
import org.bukkit.configuration.ConfigurationSection;
import me.jishuna.jishlib.config.ConfigAPI;
import me.jishuna.jishlib.config.ConfigType;
import me.jishuna.jishlib.config.CustomConfig;
import me.jishuna.jishlib.datastructure.WeightedRandom;

public class WeightedRandomAdapter<R> implements TypeAdapter<ConfigurationSection, WeightedRandom<R>> {

    private final TypeAdapterString<Object, R> adapter;

    @SuppressWarnings("unchecked")
    public WeightedRandomAdapter(ConfigType<?> type) {
        this.adapter = (TypeAdapterString<Object, R>) ConfigAPI.getStringAdapter(type.getComponentTypes().get(0));
    }

    @Override
    public Class<ConfigurationSection> getSavedType() {
        return ConfigurationSection.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<WeightedRandom<R>> getRuntimeType() {
        return (Class<WeightedRandom<R>>) (Object) WeightedRandom.class;
    }

    @Override
    public WeightedRandom<R> read(ConfigurationSection section) {
        WeightedRandom<R> random = new WeightedRandom<>();

        for (Entry<String, Object> entry : section.getValues(false).entrySet()) {
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
    public ConfigurationSection write(WeightedRandom<R> value, ConfigurationSection existing, boolean replace) {
        if (existing == null) {
            existing = new CustomConfig();
        }

        double lastKey = 0;
        for (Entry<Double, R> entry : value.getEntries()) {
            String key = this.adapter.toString(entry.getValue());

            if (!existing.isSet(key) || replace) {
                existing.set(key, entry.getKey() - lastKey);
                lastKey = entry.getKey();
            }
        }

        return existing;
    }
}

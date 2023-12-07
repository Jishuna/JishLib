package me.jishuna.jishlib.config.adapter;

import java.util.Map.Entry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import me.jishuna.jishlib.config.ConfigApi;
import me.jishuna.jishlib.config.ConfigType;
import me.jishuna.jishlib.datastructure.WeightedRandom;

public class WeightedRandomAdapter<R> implements TypeAdapter<ConfigurationSection, WeightedRandom<R>> {

    private final TypeAdapterString<Object, R> adapter;

    @SuppressWarnings("unchecked")
    public WeightedRandomAdapter(ConfigType<?> type) {
        this.adapter = (TypeAdapterString<Object, R>) ConfigApi.getStringAdapter(type.getComponentTypes().get(0));
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
    public WeightedRandom<R> read(ConfigurationSection value) {
        WeightedRandom<R> random = new WeightedRandom<>();

        for (String key : value.getKeys(false)) {
            double weight = value.getDouble(key);
            R entry = this.adapter.fromString(key);

            random.add(weight, entry);
        }
        return random;
    }

    @Override
    public ConfigurationSection write(WeightedRandom<R> value, ConfigurationSection existing, boolean replace) {
        if (existing == null) {
            existing = new YamlConfiguration();
        }

        for (Entry<Double, R> entry : value.getEntries()) {
            String key = this.adapter.toString(entry.getValue());

            if (!existing.isSet(key) || replace) {
                existing.set(key, entry.getKey());
            }
        }

        return existing;
    }
}

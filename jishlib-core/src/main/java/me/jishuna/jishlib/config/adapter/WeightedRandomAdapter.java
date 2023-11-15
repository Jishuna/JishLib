package me.jishuna.jishlib.config.adapter;

import java.util.Map.Entry;
import org.bukkit.configuration.ConfigurationSection;
import me.jishuna.jishlib.config.ConfigApi;
import me.jishuna.jishlib.config.ConfigType;
import me.jishuna.jishlib.datastructure.WeightedRandom;

public class WeightedRandomAdapter<T> implements TypeAdapter<WeightedRandom<T>> {

    private final StringAdapter<T> adapter;

    @SuppressWarnings("unchecked")
    public WeightedRandomAdapter(ConfigType<?> type) {
        this.adapter = (StringAdapter<T>) ConfigApi.getStringAdapter(type.getComponentTypes().get(0));
    }

    @Override
    public WeightedRandom<T> read(ConfigurationSection config, String path) {
        ConfigurationSection section = config.getConfigurationSection(path);
        WeightedRandom<T> random = new WeightedRandom<>();

        for (String key : section.getKeys(false)) {
            double weight = section.getDouble(key);
            T value = this.adapter.fromString(key);

            random.add(weight, value);
        }
        return random;
    }

    @Override
    public void write(ConfigurationSection config, String path, WeightedRandom<T> value, boolean replace) {
        if (config.isSet(path) && !replace) {
            return;
        }

        ConfigurationSection section = config.getConfigurationSection(path);
        if (section == null) {
            section = config.createSection(path);
        }

        for (Entry<Double, T> entry : value.getEntries()) {
            section.set(this.adapter.toString(entry.getValue()), entry.getKey());
        }
    }
}

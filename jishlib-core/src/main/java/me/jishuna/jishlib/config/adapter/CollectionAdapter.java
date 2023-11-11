package me.jishuna.jishlib.config.adapter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Supplier;
import org.bukkit.configuration.ConfigurationSection;
import me.jishuna.jishlib.config.ConfigType;
import me.jishuna.jishlib.config.ConfigurationManager;
import me.jishuna.jishlib.config.WrappedSection;

public class CollectionAdapter<V, T extends Collection<V>> implements TypeAdapter<T> {
    private static Map<Class<?>, Supplier<? extends Collection<?>>> defaults;

    static {
        defaults = new HashMap<>();
        defaults.put(List.class, ArrayList::new);
        defaults.put(Set.class, LinkedHashSet::new);
        defaults.put(Queue.class, ArrayDeque::new);
    }

    private final TypeAdapter<V> adapter;
    private final ConfigType<T> type;

    @SuppressWarnings("unchecked")
    public CollectionAdapter(ConfigurationManager manager, ConfigType<?> type) {
        this.adapter = (TypeAdapter<V>) manager.getAdapter(type.getComponentTypes().get(0));
        this.type = (ConfigType<T>) type;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T read(ConfigurationSection config, String path) {

        T collection = (T) defaults.get(this.type.getType()).get();
        if (this.adapter instanceof StringAdapter<V> stringAdapter) {
            List<String> list = config.getStringList(path);

            list.forEach(value -> collection.add(stringAdapter.fromString(value)));
            return collection;
        }

        if (!config.isList(path)) {
            return collection;
        }

        List<?> list = config.getList(path);
        for (Object value : list) {
            if (!(value instanceof Map<?, ?> map)) {
                continue;
            }

            String fullPath = path + ".key";
            config.createSection(fullPath, map);
            collection.add(this.adapter.read(config, fullPath));

        }
        return collection;
    }

    @Override
    public void write(ConfigurationSection config, String path, T value, boolean replace) {
        if (this.adapter instanceof StringAdapter<V> stringAdapter) {
            writeStrings(config, path, value, replace, stringAdapter);
            return;
        }

        List<Object> sections = new ArrayList<>();

        value.forEach(entry -> {
            WrappedSection section = new WrappedSection(config);
            this.adapter.write(section, "key", entry, replace);
            sections.add(section.getValues(false).get("key"));
        });

        config.set(path, sections);
    }

    private void writeStrings(ConfigurationSection config, String path, T value, boolean replace, StringAdapter<V> adapter) {
        List<String> list = new ArrayList<>();

        value.forEach(entry -> {
            String toAdd = adapter.toString(entry);
            if (!list.contains(toAdd)) {
                list.add(toAdd);
            }
        });
        config.set(path, list);
    }
}

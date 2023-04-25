package me.jishuna.jishlib.config.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import org.bukkit.configuration.ConfigurationSection;

import me.jishuna.jishlib.StringUtils;

public class CollectionTypeAdapter<T extends Collection<String>> implements TypeAdapter<T> {

    private final Supplier<T> supplier;

    public CollectionTypeAdapter(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T read(ConfigurationSection config, String path) {
        T collection = this.supplier.get();
        List<String> list = config.getStringList(path);

        list.forEach(value -> collection.add(StringUtils.parseToLegacy(value)));

        return collection;
    }

    @SuppressWarnings("unchecked")
    public void write(ConfigurationSection config, String path, Object value) {
        List<String> list = new ArrayList<>();
        ((T) value).forEach(list::add);
        config.set(path, list);
    }
}

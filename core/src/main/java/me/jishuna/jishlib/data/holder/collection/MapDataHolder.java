package me.jishuna.jishlib.data.holder.collection;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import me.jishuna.jishlib.data.HolderType;
import me.jishuna.jishlib.data.holder.BooleanDataHolder;
import me.jishuna.jishlib.data.holder.DataHolder;
import me.jishuna.jishlib.data.holder.StringDataHolder;
import me.jishuna.jishlib.data.holder.number.NumericDataHolder;
import me.jishuna.jishlib.data.source.DataWriter;

public class MapDataHolder extends DataHolder<Map<String, DataHolder<?>>> implements Iterable<DataHolder<?>> {
    private final String pathSeperator;

    private MapDataHolder(String name, String seperator) {
        this(name, seperator, new LinkedHashMap<>());
    }

    private MapDataHolder(String name, String seperator, Map<String, DataHolder<?>> value) {
        super(name, value);
        this.pathSeperator = seperator;
    }

    public static MapDataHolder empty(String name) {
        return new MapDataHolder(name, null);
    }

    public static MapDataHolder empty(String name, String seperator) {
        return new MapDataHolder(name, seperator);
    }

    public static MapDataHolder of(Map<String, DataHolder<?>> value) {
        return new MapDataHolder("", null, value);
    }

    public static MapDataHolder of(String name, String seperator, Map<String, DataHolder<?>> value) {
        value.forEach((k, v) -> v.setName(k));

        return new MapDataHolder(name, seperator, value);
    }

    public void set(String key, String value) {
        set(key, StringDataHolder.of(value));
    }

    public void set(String key, Number value) {
        set(key, NumericDataHolder.of(value));
    }

    public void set(String key, Boolean value) {
        set(key, BooleanDataHolder.of(value));
    }

    public void set(String key, DataHolder<?> value) {
        set(key, value, true);
    }

    public void set(String key, DataHolder<?> value, boolean replace) {
        if (!value.getName().equals(key)) {
            value.setName(key);
        }

        if (replace) {
            processKey(key, true, (k, v) -> v.value.put(k, value));
        } else {
            processKey(key, true, (k, v) -> v.value.putIfAbsent(k, value));
        }
    }

    public DataHolder<?> get(String key) {
        return get(key, (DataHolder<?>) null);
    }

    public DataHolder<?> get(String key, DataHolder<?> def) {
        DataHolder<?> result = processKey(key, false, (k, v) -> v.value.get(k));
        return result == null ? def : result;
    }

    public <T> T get(String key, Class<T> type) {
        return get(key, type, null);
    }

    public <T> Optional<T> find(String key, Class<T> type) {
        return Optional.ofNullable(get(key, type, null));
    }

    public <T> T get(String key, Class<T> type, T def) {
        DataHolder<?> result = get(key, (DataHolder<?>) null);
        if (type.isInstance(result.get())) {
            return type.cast(result.get());
        }

        return def;
    }

    public void forEach(BiConsumer<String, DataHolder<?>> consumer) {
        this.value.forEach(consumer);
    }

    @Override
    public Iterator<DataHolder<?>> iterator() {
        return this.value.values().iterator();
    }

    public void merge(MapDataHolder other) {
        other.forEach((k, v) -> {
            if (v instanceof MapDataHolder map) {
                get(k, MapDataHolder.class, MapDataHolder.empty(this.pathSeperator)).merge(map);
            } else if (v instanceof ListDataHolder list) {
                get(k, ListDataHolder.class, ListDataHolder.empty(k)).merge(list);
            } else {
                set(k, v);
            }
        });
    }

    @Override
    public void write(DataWriter<?> writer) throws IOException {
        writer.writeMap(this.name, this);
    }

    @Override
    public HolderType getType() {
        return HolderType.MAP;
    }

    private <R> R processKey(String key, boolean create, BiFunction<String, MapDataHolder, R> function) {
        if (this.pathSeperator == null) {
            return function.apply(key, this);
        }

        int index = 0;
        MapDataHolder target = this;
        String subKey;
        MapDataHolder subValue;

        while ((index = key.indexOf(this.pathSeperator)) != -1) {
            subKey = key.substring(0, index);
            subValue = target.get(subKey, MapDataHolder.class);
            if (subValue == null) {
                if (!create) {
                    return null;
                }
                subValue = new MapDataHolder(subKey, this.pathSeperator);
                target.value.put(subKey, subValue);
            }

            target = subValue;
            key = key.substring(index + 1);
        }

        return function.apply(key, target);
    }
}

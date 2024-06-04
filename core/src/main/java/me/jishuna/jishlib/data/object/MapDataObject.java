package me.jishuna.jishlib.data.object;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class MapDataObject extends DataObject<Map<String, DataObject<?>>> {
    private final String pathSeperator;

    private MapDataObject(String seperator) {
        this(seperator, new LinkedHashMap<>());
    }

    private MapDataObject(String seperator, Map<String, DataObject<?>> value) {
        super(value);
        this.pathSeperator = seperator;
    }

    public static MapDataObject empty() {
        return new MapDataObject(null);
    }

    public static MapDataObject empty(String seperator) {
        return new MapDataObject(seperator);
    }

    public static MapDataObject of(Map<String, DataObject<?>> value) {
        return new MapDataObject(null, value);
    }

    public static MapDataObject of(String seperator, Map<String, DataObject<?>> value) {
        return new MapDataObject(seperator, value);
    }

    public void set(String key, DataObject<?> value, boolean replace) {
        if (replace) {
            processKey(key, true, (k, v) -> v.value.put(k, value));
        } else {
            processKey(key, true, (k, v) -> v.value.putIfAbsent(k, value));
        }
    }

    public DataObject<?> get(String key) {
        return processKey(key, false, (k, v) -> v.value.get(k));
    }

    public <T extends DataObject<?>> T get(String key, Class<T> type) {
        DataObject<?> obj = get(key);
        if (type.isInstance(obj)) {
            return type.cast(obj);
        }

        return null;
    }

    public <T extends DataObject<?>> T get(String key, Class<T> type, T def) {
        DataObject<?> obj = get(key);
        if (type.isInstance(obj)) {
            return type.cast(obj);
        }

        return def;
    }

    public PrimitiveDataObject getPrimitive(String key) {
        return get(key, PrimitiveDataObject.class);
    }

    public void forEach(BiConsumer<String, DataObject<?>> consumer) {
        this.value.forEach(consumer);
    }

    public void merge(MapDataObject other) {
        other.forEach((k, v) -> {
            if (v instanceof MapDataObject map) {
                get(k, MapDataObject.class, MapDataObject.empty(this.pathSeperator)).merge(map);
            } else if (v instanceof ListDataObject list) {
                get(k, ListDataObject.class, ListDataObject.empty()).merge(list);
            } else {
                set(k, v, false);
            }
        });
    }

    @Override
    public Object serialize() {
        Map<String, Object> map = new HashMap<>();
        this.value.forEach((k, v) -> map.put(k, v.serialize()));

        return map;
    }

    private <R> R processKey(String key, boolean create, BiFunction<String, MapDataObject, R> function) {
        if (this.pathSeperator == null) {
            return function.apply(key, this);
        }

        int index = 0;
        MapDataObject target = this;
        String subKey;
        MapDataObject subValue;

        while ((index = key.indexOf(this.pathSeperator)) != -1) {
            subKey = key.substring(0, index);
            subValue = target.get(subKey, MapDataObject.class);
            if (subValue == null) {
                if (!create) {
                    return null;
                }
                subValue = new MapDataObject(this.pathSeperator);
                target.value.put(subKey, subValue);
            }

            target = subValue;
            key = key.substring(index + 1);
        }

        return function.apply(key, target);
    }
}

package me.jishuna.jishlib.data.object;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class MapDataObject extends DataObject<Map<String, DataObject<?>>> {

    private MapDataObject() {
        this(new LinkedHashMap<>());
    }

    private MapDataObject(Map<String, DataObject<?>> value) {
        super(value);
    }

    public static MapDataObject of(Map<String, DataObject<?>> value) {
        return new MapDataObject(value);
    }

    public void set(String key, DataObject<?> value, boolean replace) {
        int index = 0;
        MapDataObject target = this;
        MapDataObject subValue;
        String subKey;

        while ((index = key.indexOf('.')) != -1) {
            subKey = key.substring(0, index);
            subValue = target.get(subKey, MapDataObject.class);
            if (subValue == null) {
                subValue = new MapDataObject();
                target.value.put(subKey, subValue);
            }

            target = subValue;
            key = key.substring(index + 1);
        }

        if (replace) {
            target.value.put(key, value);
        } else {
            target.value.putIfAbsent(key, value);
        }
    }

    public DataObject<?> get(String key) {
        int index = 0;
        MapDataObject target = this;
        String subKey;

        while ((index = key.indexOf('.')) != -1) {
            subKey = key.substring(0, index);
            target = target.get(subKey, MapDataObject.class);
            if (target == null) {
                return null;
            }

            key = key.substring(index + 1);
        }

        return target.value.get(key);
    }

    public <T extends DataObject<?>> T get(String key, Class<T> type) {
        DataObject<?> obj = get(key);
        if (type.isInstance(obj)) {
            return type.cast(obj);
        }

        return null;
    }

    public void forEach(BiConsumer<String, DataObject<?>> consumer) {
        this.value.forEach(consumer);
    }

    @Override
    public Object asObject() {
        Map<String, Object> map = new HashMap<>();
        this.value.forEach((k, v) -> map.put(k, v.asObject()));

        return map;
    }
}

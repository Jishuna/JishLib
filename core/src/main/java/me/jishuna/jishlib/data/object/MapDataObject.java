package me.jishuna.jishlib.data.object;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class MapDataObject extends DataObject<Map<String, DataObject<?>>> {

    private MapDataObject(Map<String, DataObject<?>> value) {
        super(value);
    }

    public static MapDataObject of(Map<String, DataObject<?>> value) {
        return new MapDataObject(value);
    }

    public void set(String key, DataObject<?> value) {
        this.value.put(key, value);
    }

    public DataObject<?> get(String key) {
        return this.value.get(key);
    }

    public <T extends DataObject<?>> T get(String key, Class<T> type) {
        DataObject<?> obj = this.value.get(key);
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

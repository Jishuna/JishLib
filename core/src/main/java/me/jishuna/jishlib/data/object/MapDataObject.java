package me.jishuna.jishlib.data.object;

import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import me.jishuna.jishlib.data.source.nbt.TagType;

public class MapDataObject extends DataObject<Map<String, DataObject<?>>> {
    private final String pathSeperator;

    private MapDataObject(String name, String seperator) {
        this(name, seperator, new LinkedHashMap<>());
    }

    private MapDataObject(String name, String seperator, Map<String, DataObject<?>> value) {
        super(name, value);
        this.pathSeperator = seperator;
    }

    public static MapDataObject empty(String name) {
        return new MapDataObject(name, null);
    }

    public static MapDataObject empty(String name, String seperator) {
        return new MapDataObject(name, seperator);
    }

    public static MapDataObject of(Map<String, DataObject<?>> value) {
        return new MapDataObject("", null, value);
    }

    public static MapDataObject of(String name, String seperator, Map<String, DataObject<?>> value) {
        return new MapDataObject(name, seperator, value);
    }

    public void set(String key, String value) {
        set(key, StringDataObject.of(value));
    }

    public void set(String key, Number value) {
        set(key, NumericDataObject.of(value));
    }

    public void set(String key, Boolean value) {
        set(key, BooleanDataObject.of(value));
    }

    public void set(String key, DataObject<?> value) {
        set(key, value, true);
    }

    public void set(String key, DataObject<?> value, boolean replace) {
        if (replace) {
            processKey(key, true, (k, v) -> v.value.put(k, value));
        } else {
            processKey(key, true, (k, v) -> v.value.putIfAbsent(k, value));
        }
    }

    public <T extends DataObject<?>> T get(String key, Class<T> type) {
        return get(key, type, null);
    }

    public <T extends DataObject<?>> T get(String key, Class<T> type, T def) {
        DataObject<?> obj = get(key);
        if (type.isInstance(obj)) {
            return type.cast(obj);
        }

        return def;
    }

    public DataObject<?> get(String key) {
        return processKey(key, false, (k, v) -> v.value.get(k));
    }

    public byte getByte(String key) {
        DataObject<?> obj = get(key);
        if (obj instanceof NumericDataObject<?> numeric) {
            return numeric.byteValue();
        }

        return 0;
    }

    public short getShort(String key) {
        DataObject<?> obj = get(key);
        if (obj instanceof NumericDataObject<?> numeric) {
            return numeric.shortValue();
        }

        return 0;
    }

    public int getInt(String key) {
        DataObject<?> obj = get(key);
        if (obj instanceof NumericDataObject<?> numeric) {
            return numeric.intValue();
        }

        return 0;
    }

    public long getLong(String key) {
        DataObject<?> obj = get(key);
        if (obj instanceof NumericDataObject<?> numeric) {
            return numeric.longValue();
        }

        return 0;
    }

    public float getFloat(String key) {
        DataObject<?> obj = get(key);
        if (obj instanceof NumericDataObject<?> numeric) {
            return numeric.floatValue();
        }

        return 0;
    }

    public double getDouble(String key) {
        DataObject<?> obj = get(key);
        if (obj instanceof NumericDataObject<?> numeric) {
            return numeric.doubleValue();
        }

        return 0;
    }

    public void forEach(BiConsumer<String, DataObject<?>> consumer) {
        this.value.forEach(consumer);
    }

    public void merge(MapDataObject other) {
        other.forEach((k, v) -> {
            if (v instanceof MapDataObject map) {
                get(k, MapDataObject.class, MapDataObject.empty(this.pathSeperator)).merge(map);
            } else if (v instanceof ListDataObject list) {
                get(k, ListDataObject.class, ListDataObject.empty(k)).merge(list);
            } else {
                set(k, v);
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
                subValue = new MapDataObject(subKey, this.pathSeperator);
                target.value.put(subKey, subValue);
            }

            target = subValue;
            key = key.substring(index + 1);
        }

        return function.apply(key, target);
    }

    @Override
    public byte getTagType() {
        return TagType.COMPOUND.id();
    }

    @Override
    public void write(DataOutput output) throws IOException {
        for (DataObject<?> object : this.value.values()) {
            output.writeByte(object.getTagType());
            output.writeUTF(object.getName());

            object.write(output);
        }

        output.writeByte(0);
    }
}

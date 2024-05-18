package me.jishuna.jishlib.data.source;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.configuration.ConfigurationSection;
import me.jishuna.jishlib.data.object.BooleanDataObject;
import me.jishuna.jishlib.data.object.DataObject;
import me.jishuna.jishlib.data.object.ListDataObject;
import me.jishuna.jishlib.data.object.MapDataObject;
import me.jishuna.jishlib.data.object.NumericDataObject;
import me.jishuna.jishlib.data.object.StringDataObject;

public class YamlDataSource {

    public static MapDataObject read(ConfigurationSection configuration) {
        return readMap(configuration.getValues(false));
    }

    public static void write(MapDataObject data, ConfigurationSection configuration) {
        data.forEach((k, v) -> configuration.set(k, v.asObject()));
    }

    private static MapDataObject readMap(Map<?, ?> map) {
        Map<String, DataObject<?>> dataMap = new LinkedHashMap<>();
        map.forEach((k, v) -> {
            dataMap.put(String.valueOf(k), readValue(v));
        });

        return MapDataObject.of(dataMap);
    }

    private static ListDataObject readList(Collection<?> list) {
        List<DataObject<?>> dataList = new ArrayList<>();
        for (Object obj : list) {
            dataList.add(readValue(obj));
        }

        return ListDataObject.of(dataList);
    }

    private static DataObject<?> readValue(Object value) {
        if (Map.class.isInstance(value)) {
            return readMap((Map<?, ?>) value);
        }

        if (ConfigurationSection.class.isInstance(value)) {
            return readMap(((ConfigurationSection) value).getValues(false));
        }

        if (Collection.class.isInstance(value)) {
            return readList((Collection<?>) value);
        }

        if (Boolean.class.isInstance(value)) {
            return BooleanDataObject.of((boolean) value);
        }

        if (Number.class.isInstance(value)) {
            return NumericDataObject.of((Number) value);
        }

        return StringDataObject.of(String.valueOf(value));
    }
}

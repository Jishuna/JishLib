package me.jishuna.jishlib.data.source;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import me.jishuna.jishlib.data.object.BooleanDataObject;
import me.jishuna.jishlib.data.object.DataObject;
import me.jishuna.jishlib.data.object.ListDataObject;
import me.jishuna.jishlib.data.object.MapDataObject;
import me.jishuna.jishlib.data.object.NumericDataObject;
import me.jishuna.jishlib.data.object.StringDataObject;

public class YamlDataSource {

    public static MapDataObject parse(YamlConfiguration configuration) {
        return parseMap(configuration.getValues(false));
    }

    private static MapDataObject parseMap(Map<?, ?> map) {
        Map<String, DataObject<?>> dataMap = new LinkedHashMap<>();
        map.forEach((k, v) -> {
            dataMap.put(String.valueOf(k), parseValue(v));
        });

        return MapDataObject.of(dataMap);
    }

    private static ListDataObject parseList(Collection<?> list) {
        List<DataObject<?>> dataList = new ArrayList<>();
        for (Object obj : list) {
            dataList.add(parseValue(obj));
        }

        return ListDataObject.of(dataList);
    }

    private static DataObject<?> parseValue(Object value) {
        if (Map.class.isInstance(value)) {
            return parseMap((Map<?, ?>) value);
        }

        if (ConfigurationSection.class.isInstance(value)) {
            return parseMap(((ConfigurationSection) value).getValues(false));
        }

        if (Collection.class.isInstance(value)) {
            return parseList((Collection<?>) value);
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

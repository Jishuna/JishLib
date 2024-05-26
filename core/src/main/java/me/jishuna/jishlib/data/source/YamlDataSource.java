package me.jishuna.jishlib.data.source;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import me.jishuna.jishlib.data.object.DataObject;
import me.jishuna.jishlib.data.object.ListDataObject;
import me.jishuna.jishlib.data.object.MapDataObject;
import me.jishuna.jishlib.data.object.PrimitiveDataObject;

public class YamlDataSource implements DataSource {

    @Override
    public MapDataObject read(File file) {
        return read(YamlConfiguration.loadConfiguration(file));
    }

    @Override
    public void write(MapDataObject data, File file) {
        write(data, YamlConfiguration.loadConfiguration(file));
    }

    public MapDataObject read(ConfigurationSection configuration) {
        return readMap(configuration.getValues(false));
    }

    public void write(MapDataObject data, ConfigurationSection configuration) {
        data.forEach((k, v) -> configuration.set(k, v.serialize()));
    }

    private MapDataObject readMap(Map<?, ?> map) {
        Map<String, DataObject<?>> dataMap = new LinkedHashMap<>();
        map.forEach((k, v) -> {
            dataMap.put(String.valueOf(k), readValue(v));
        });

        return MapDataObject.of(dataMap);
    }

    private ListDataObject readList(Collection<?> list) {
        List<DataObject<?>> dataList = new ArrayList<>();
        for (Object obj : list) {
            dataList.add(readValue(obj));
        }

        return ListDataObject.of(dataList);
    }

    private DataObject<?> readValue(Object value) {
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
            return PrimitiveDataObject.of((boolean) value);
        }

        if (Number.class.isInstance(value)) {
            return PrimitiveDataObject.of((Number) value);
        }

        return PrimitiveDataObject.of(String.valueOf(value));
    }
}

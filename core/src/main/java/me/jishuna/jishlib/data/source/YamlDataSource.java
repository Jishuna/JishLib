package me.jishuna.jishlib.data.source;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
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

public class YamlDataSource implements DataSource {
    private final String pathSeperator;

    public YamlDataSource(String pathSeperator) {
        this.pathSeperator = pathSeperator;
    }

    @Override
    public MapDataObject read(File file) {
        return read(YamlConfiguration.loadConfiguration(file));
    }

    @Override
    public MapDataObject read(Reader reader) {
        try (reader) {
            return read(YamlConfiguration.loadConfiguration(reader));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return MapDataObject.empty("");
    }

    @Override
    public void write(MapDataObject data, File file) {
        YamlConfiguration config = new YamlConfiguration();
        write(data, config);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            DataObject<?> value = readValue(v);
            value.setName(String.valueOf(k));
            dataMap.put(value.getName(), value);
        });

        return MapDataObject.of("", this.pathSeperator, dataMap);
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
            return BooleanDataObject.of((boolean) value);
        }

        if (Number.class.isInstance(value)) {
            return NumericDataObject.of((Number) value);
        }

        return StringDataObject.of(String.valueOf(value));
    }
}

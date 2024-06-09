package me.jishuna.jishlib.data.source.yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import me.jishuna.jishlib.data.source.DataReader;

public class YamlReader implements DataReader {
    private final String pathSeperator;

    private YamlReader(String pathSeperator) {
        this.pathSeperator = pathSeperator;
    }

    public static YamlReader create(String pathSeperator) {
        return new YamlReader(pathSeperator);
    }

    @Override
    public MapDataObject readFile(File file) throws IOException {
        return read(YamlConfiguration.loadConfiguration(file));
    }

    @Override
    public MapDataObject readStream(InputStream stream) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(stream)) {
            return read(YamlConfiguration.loadConfiguration(reader));
        }
    }

    public MapDataObject read(YamlConfiguration config) {
        return readMap(config.getValues(false));
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

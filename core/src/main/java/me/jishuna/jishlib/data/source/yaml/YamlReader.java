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
import me.jishuna.jishlib.data.holder.BooleanDataHolder;
import me.jishuna.jishlib.data.holder.DataHolder;
import me.jishuna.jishlib.data.holder.StringDataHolder;
import me.jishuna.jishlib.data.holder.collection.ListDataHolder;
import me.jishuna.jishlib.data.holder.collection.MapDataHolder;
import me.jishuna.jishlib.data.holder.number.NumericDataHolder;
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
    public MapDataHolder readFile(File file) throws IOException {
        return read(YamlConfiguration.loadConfiguration(file));
    }

    @Override
    public MapDataHolder readStream(InputStream stream) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(stream)) {
            return read(YamlConfiguration.loadConfiguration(reader));
        }
    }

    public MapDataHolder read(YamlConfiguration config) {
        return readMap(config.getValues(false));
    }

    private MapDataHolder readMap(Map<?, ?> map) {
        Map<String, DataHolder<?>> dataMap = new LinkedHashMap<>();
        map.forEach((k, v) -> {
            DataHolder<?> value = readValue(v);
            value.setName(String.valueOf(k));
            dataMap.put(value.getName(), value);
        });

        return MapDataHolder.of("", this.pathSeperator, dataMap);
    }

    private ListDataHolder readList(Collection<?> list) {
        List<DataHolder<?>> dataList = new ArrayList<>();
        for (Object obj : list) {
            dataList.add(readValue(obj));
        }

        return ListDataHolder.of(dataList);
    }

    private DataHolder<?> readValue(Object value) {
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
            return BooleanDataHolder.of((boolean) value);
        }

        if (Number.class.isInstance(value)) {
            return NumericDataHolder.of((Number) value);
        }

        return StringDataHolder.of(String.valueOf(value));
    }
}

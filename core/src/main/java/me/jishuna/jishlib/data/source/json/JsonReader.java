package me.jishuna.jishlib.data.source.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import me.jishuna.jishlib.data.holder.BooleanDataHolder;
import me.jishuna.jishlib.data.holder.DataHolder;
import me.jishuna.jishlib.data.holder.StringDataHolder;
import me.jishuna.jishlib.data.holder.collection.ListDataHolder;
import me.jishuna.jishlib.data.holder.collection.MapDataHolder;
import me.jishuna.jishlib.data.holder.number.NumericDataHolder;
import me.jishuna.jishlib.data.source.DataReader;

public class JsonReader implements DataReader {
    private final String pathSeperator;

    private JsonReader(String pathSeperator) {
        this.pathSeperator = pathSeperator;
    }

    public static JsonReader create(String pathSeperator) {
        return new JsonReader(pathSeperator);
    }

    @Override
    public MapDataHolder readFile(File file) throws IOException {
        try (FileReader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            return readObject(JsonParser.parseReader(reader).getAsJsonObject());
        }
    }

    @Override
    public MapDataHolder readStream(InputStream stream) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(stream)) {
            return readObject(JsonParser.parseReader(reader).getAsJsonObject());
        }
    }

    private MapDataHolder readObject(JsonObject json) {
        Map<String, DataHolder<?>> dataMap = new LinkedHashMap<>();
        json.asMap().forEach((k, v) -> {
            dataMap.put(k, readValue(v));
        });

        return MapDataHolder.of("", this.pathSeperator, dataMap);
    }

    private ListDataHolder readArray(JsonArray array) {
        List<DataHolder<?>> dataList = new ArrayList<>();
        array.forEach(v -> dataList.add(readValue(v)));

        return ListDataHolder.of(dataList);
    }

    private DataHolder<?> readValue(JsonElement value) {
        if (value.isJsonObject()) {
            return readObject(value.getAsJsonObject());
        }

        if (value.isJsonArray()) {
            return readArray(value.getAsJsonArray());
        }

        if (value.isJsonPrimitive()) {
            JsonPrimitive primitive = value.getAsJsonPrimitive();
            if (primitive.isBoolean()) {
                return BooleanDataHolder.of(primitive.getAsBoolean());
            }

            if (primitive.isNumber()) {
                return NumericDataHolder.of(primitive.getAsNumber());
            }
        }

        return StringDataHolder.of(value.getAsString());
    }
}

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
import me.jishuna.jishlib.data.object.BooleanDataObject;
import me.jishuna.jishlib.data.object.DataObject;
import me.jishuna.jishlib.data.object.ListDataObject;
import me.jishuna.jishlib.data.object.MapDataObject;
import me.jishuna.jishlib.data.object.NumericDataObject;
import me.jishuna.jishlib.data.object.StringDataObject;
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
    public MapDataObject readFile(File file) throws IOException {
        try (FileReader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            return readObject(JsonParser.parseReader(reader).getAsJsonObject());
        }
    }

    @Override
    public MapDataObject readStream(InputStream stream) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(stream)) {
            return readObject(JsonParser.parseReader(reader).getAsJsonObject());
        }
    }

    private MapDataObject readObject(JsonObject json) {
        Map<String, DataObject<?>> dataMap = new LinkedHashMap<>();
        json.asMap().forEach((k, v) -> {
            dataMap.put(k, readValue(v));
        });

        return MapDataObject.of("", this.pathSeperator, dataMap);
    }

    private ListDataObject readArray(JsonArray array) {
        List<DataObject<?>> dataList = new ArrayList<>();
        array.forEach(v -> dataList.add(readValue(v)));

        return ListDataObject.of(dataList);
    }

    private DataObject<?> readValue(JsonElement value) {
        if (value.isJsonObject()) {
            return readObject(value.getAsJsonObject());
        }

        if (value.isJsonArray()) {
            return readArray(value.getAsJsonArray());
        }

        if (value.isJsonPrimitive()) {
            JsonPrimitive primitive = value.getAsJsonPrimitive();
            if (primitive.isBoolean()) {
                return BooleanDataObject.of(primitive.getAsBoolean());
            }

            if (primitive.isNumber()) {
                return NumericDataObject.of(primitive.getAsNumber());
            }
        }

        return StringDataObject.of(value.getAsString());
    }
}

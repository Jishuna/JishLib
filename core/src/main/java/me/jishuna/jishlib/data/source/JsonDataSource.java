package me.jishuna.jishlib.data.source;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import me.jishuna.jishlib.data.object.DataObject;
import me.jishuna.jishlib.data.object.ListDataObject;
import me.jishuna.jishlib.data.object.MapDataObject;
import me.jishuna.jishlib.data.object.PrimitiveDataObject;

public class JsonDataSource implements DataSource {
    private static final Gson GSON = new GsonBuilder()
            .setLenient()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();
    private static final Type TYPE = new TypeToken<Map<String, Object>>() {
    }.getType();

    private final String pathSeperator;

    public JsonDataSource(String pathSeperator) {
        this.pathSeperator = pathSeperator;
    }

    @Override
    public MapDataObject read(File file) {
        try (FileReader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            return read(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return MapDataObject.empty();
    }

    @Override
    public MapDataObject read(Reader reader) {
        try (reader) {
            return readObject(JsonParser.parseReader(reader).getAsJsonObject());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return MapDataObject.empty();
    }

    @Override
    public void write(MapDataObject data, File file) {
        try {
            Files.createParentDirs(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Writer writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            GSON.toJson(data.serialize(), TYPE, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MapDataObject readObject(JsonObject json) {
        Map<String, DataObject<?>> dataMap = new LinkedHashMap<>();
        json.asMap().forEach((k, v) -> {
            dataMap.put(k, readValue(v));
        });

        return MapDataObject.of(this.pathSeperator, dataMap);
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
                return PrimitiveDataObject.of(primitive.getAsBoolean());
            }

            if (primitive.isNumber()) {
                return PrimitiveDataObject.of(primitive.getAsNumber());
            }
        }

        return PrimitiveDataObject.of(value.getAsString());
    }
}

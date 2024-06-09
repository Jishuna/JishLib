package me.jishuna.jishlib.data.source.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;
import me.jishuna.jishlib.data.object.ArrayDataObject;
import me.jishuna.jishlib.data.object.BooleanDataObject;
import me.jishuna.jishlib.data.object.DataObject;
import me.jishuna.jishlib.data.object.ListDataObject;
import me.jishuna.jishlib.data.object.MapDataObject;
import me.jishuna.jishlib.data.object.NumericDataObject;
import me.jishuna.jishlib.data.object.StringDataObject;
import me.jishuna.jishlib.data.source.DataWriter;

public class JsonWriter implements DataWriter {
    private static final Gson GSON = new GsonBuilder()
            .setLenient()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    private final File file;
    private final Stack<JsonElement> stack = new Stack<>();

    private JsonWriter(File file, JsonObject json) {
        this.file = file;
        this.stack.add(json);
    }

    public static JsonWriter create(File file) {
        return new JsonWriter(file, new JsonObject());
    }

    public static JsonWriter create(File file, JsonObject json) {
        return new JsonWriter(file, json);
    }

    @Override
    public void writeMap(String name, MapDataObject value) throws IOException {
        JsonObject json = new JsonObject();
        this.stack.push(json);

        for (DataObject<?> v : value.get().values()) {
            v.write(this);
        }

        this.stack.pop();

        JsonElement element = this.stack.peek();
        if (element.isJsonArray()) {
            element.getAsJsonArray().add(json);
        } else if (element.isJsonObject()) {
            element.getAsJsonObject().add(name, json);
        }
    }

    @Override
    public void writeList(String name, ListDataObject value) throws IOException {
        JsonArray json = new JsonArray();
        this.stack.push(json);

        for (DataObject<?> v : value) {
            v.write(this);
        }

        this.stack.pop();

        JsonElement element = this.stack.peek();
        if (element.isJsonArray()) {
            element.getAsJsonArray().add(json);
        } else if (element.isJsonObject()) {
            element.getAsJsonObject().add(name, json);
        }
    }

    @Override
    public void writeArray(String name, ArrayDataObject value) throws IOException {
        writeList(name, value);
    }

    @Override
    public void writeString(String name, StringDataObject value) {
        JsonElement element = this.stack.peek();
        if (element.isJsonArray()) {
            element.getAsJsonArray().add(value.get());
        } else if (element.isJsonObject()) {
            element.getAsJsonObject().addProperty(name, value.get());
        }
    }

    @Override
    public void writeNumber(String name, NumericDataObject<?> value) {
        JsonElement element = this.stack.peek();
        if (element.isJsonArray()) {
            element.getAsJsonArray().add(value.get());
        } else if (element.isJsonObject()) {
            element.getAsJsonObject().addProperty(name, value.get());
        }
    }

    @Override
    public void writeBoolean(String name, BooleanDataObject value) {
        JsonElement element = this.stack.peek();
        if (element.isJsonArray()) {
            element.getAsJsonArray().add(value.get());
        } else if (element.isJsonObject()) {
            element.getAsJsonObject().addProperty(name, value.get());
        }
    }

    @Override
    public void close() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.file))) {
            GSON.toJson(this.stack.peek(), writer);
        }
    }
}

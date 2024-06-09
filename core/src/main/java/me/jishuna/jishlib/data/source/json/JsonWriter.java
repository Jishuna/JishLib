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

    private final JsonObject root;
    private final Stack<JsonElement> stack = new Stack<>();

    private JsonWriter(JsonObject json) {
        this.root = json;
        this.stack.add(this.root);
    }

    public static JsonWriter create() {
        return new JsonWriter(new JsonObject());
    }

    public static JsonWriter create(JsonObject json) {
        return new JsonWriter(json);
    }

    @Override
    public void save(File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            GSON.toJson(this.root, writer);
        }
    }

    @Override
    public void writeMap(String name, MapDataObject value) throws IOException {
        boolean pop = false;
        if (!name.isBlank()) {
            JsonObject json = new JsonObject();
            JsonElement element = this.stack.peek();

            if (element.isJsonArray()) {
                element.getAsJsonArray().add(json);
            } else if (element.isJsonObject()) {
                element.getAsJsonObject().add(name, json);
            }

            this.stack.push(json);
            pop = true;
        }

        for (DataObject<?> v : value.get().values()) {
            v.write(this);
        }

        if (pop) {
            this.stack.pop();
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

    public JsonObject getValue() {
        return this.root;
    }
}

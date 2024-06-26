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
import java.util.ArrayDeque;
import java.util.Deque;
import me.jishuna.jishlib.data.holder.BooleanDataHolder;
import me.jishuna.jishlib.data.holder.DataHolder;
import me.jishuna.jishlib.data.holder.StringDataHolder;
import me.jishuna.jishlib.data.holder.collection.ArrayDataHolder;
import me.jishuna.jishlib.data.holder.collection.ListDataHolder;
import me.jishuna.jishlib.data.holder.collection.MapDataHolder;
import me.jishuna.jishlib.data.holder.number.ByteDataHolder;
import me.jishuna.jishlib.data.holder.number.DoubleDataHolder;
import me.jishuna.jishlib.data.holder.number.FloatDataHolder;
import me.jishuna.jishlib.data.holder.number.IntDataHolder;
import me.jishuna.jishlib.data.holder.number.LongDataHolder;
import me.jishuna.jishlib.data.holder.number.NumericDataHolder;
import me.jishuna.jishlib.data.holder.number.ShortDataHolder;
import me.jishuna.jishlib.data.source.DataWriter;

public class JsonWriter implements DataWriter<JsonObject> {
    private static final Gson GSON = new GsonBuilder()
            .setLenient()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    private final JsonObject root;
    private final Deque<JsonElement> stack = new ArrayDeque<>();

    private JsonWriter(JsonObject json) {
        this.root = json;
        this.stack.add(json);
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
    public void writeMap(String name, MapDataHolder value) throws IOException {
        if (this.stack.size() == 1 && name.isBlank()) {
            for (DataHolder<?> v : value) {
                v.write(this);
            }
            return;
        }

        JsonObject json = new JsonObject();
        this.stack.addFirst(json);

        for (DataHolder<?> v : value) {
            v.write(this);
        }

        this.stack.removeFirst();
        JsonElement element = this.stack.peekFirst();
        if (element.isJsonArray()) {
            element.getAsJsonArray().add(json);
        } else if (element.isJsonObject()) {
            element.getAsJsonObject().add(name, json);
        }
    }

    @Override
    public void writeList(String name, ListDataHolder value) throws IOException {
        JsonArray json = new JsonArray();
        this.stack.addFirst(json);

        for (DataHolder<?> v : value) {
            v.write(this);
        }

        this.stack.removeFirst();
        JsonElement element = this.stack.peekFirst();
        if (element.isJsonArray()) {
            element.getAsJsonArray().add(json);
        } else if (element.isJsonObject()) {
            element.getAsJsonObject().add(name, json);
        }
    }

    @Override
    public void writeArray(String name, ArrayDataHolder value) throws IOException {
        writeList(name, value);
    }

    @Override
    public void writeString(String name, StringDataHolder value) {
        JsonElement element = this.stack.peekFirst();
        if (element.isJsonArray()) {
            element.getAsJsonArray().add(value.get());
        } else if (element.isJsonObject()) {
            element.getAsJsonObject().addProperty(name, value.get());
        }
    }

    @Override
    public void writeByte(String name, ByteDataHolder value) throws IOException {
        write(name, value);
    }

    @Override
    public void writeShort(String name, ShortDataHolder value) throws IOException {
        write(name, value);
    }

    @Override
    public void writeInt(String name, IntDataHolder value) throws IOException {
        write(name, value);
    }

    @Override
    public void writeLong(String name, LongDataHolder value) throws IOException {
        write(name, value);
    }

    @Override
    public void writeFloat(String name, FloatDataHolder value) throws IOException {
        write(name, value);
    }

    @Override
    public void writeDouble(String name, DoubleDataHolder value) throws IOException {
        write(name, value);
    }

    @Override
    public void writeBoolean(String name, BooleanDataHolder value) {
        JsonElement element = this.stack.peekFirst();
        if (element.isJsonArray()) {
            element.getAsJsonArray().add(value.get());
        } else if (element.isJsonObject()) {
            element.getAsJsonObject().addProperty(name, value.get());
        }
    }

    @Override
    public void close() throws IOException {
        // Nothing to close
    }

    @Override
    public JsonObject getValue() {
        return this.root;
    }

    private void write(String name, NumericDataHolder<?> value) {
        JsonElement element = this.stack.peekFirst();
        if (element.isJsonArray()) {
            element.getAsJsonArray().add(value.get());
        } else if (element.isJsonObject()) {
            element.getAsJsonObject().addProperty(name, value.get());
        }
    }
}

package me.jishuna.jishlib.message;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import me.jishuna.jishlib.Constants;
import me.jishuna.jishlib.Plugin;
import me.jishuna.jishlib.data.holder.StringDataHolder;
import me.jishuna.jishlib.data.holder.collection.ListDataHolder;
import me.jishuna.jishlib.data.holder.collection.MapDataHolder;
import me.jishuna.jishlib.data.source.json.JsonReader;
import me.jishuna.jishlib.data.source.json.JsonWriter;

public class Messages {
    private static Messages INSTANCE;

    public static void initialize(String path) {
        if (INSTANCE != null) {
            throw new IllegalStateException("Messages already initialized");
        }

        INSTANCE = new Messages(path);
        INSTANCE.load();
    }

    public static void reload() {
        INSTANCE.load();
    }

    public static Component get(String key) {
        String message = INSTANCE.strings.getOrDefault(key, key);
        return Constants.MINI_MESSAGE.deserialize(message);
    }

    public static Component get(String key, TagResolver... resolvers) {
        String message = INSTANCE.strings.getOrDefault(key, key);
        return Constants.MINI_MESSAGE.deserialize(message, resolvers);
    }

    public static List<Component> getList(String key) {
        List<String> strings = INSTANCE.stringLists.getOrDefault(key, Collections.emptyList());
        List<Component> components = new ArrayList<>();

        strings.forEach(s -> components.add(Constants.MINI_MESSAGE.deserialize(s)));
        return components;
    }

    public static List<Component> getList(String key, TagResolver... resolvers) {
        List<String> strings = INSTANCE.stringLists.getOrDefault(key, Collections.emptyList());
        List<Component> components = new ArrayList<>();

        strings.forEach(s -> components.add(Constants.MINI_MESSAGE.deserialize(s, resolvers)));
        return components;
    }

    private final String path;
    private final File file;
    private final Map<String, String> strings = new ConcurrentHashMap<>();
    private final Map<String, List<String>> stringLists = new ConcurrentHashMap<>();

    private Messages(String path) {
        this.path = path;
        this.file = new File(Plugin.getInstance().getDataFolder(), path);
    }

    private void load() {
        JsonReader source = JsonReader.create(null);

        MapDataHolder saved = readSaved(source);
        MapDataHolder internal = readInternal(source);

        saved.merge(internal);
        try {
            JsonWriter writer = JsonWriter.create();
            writer.writeMap("", saved);
            writer.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadValues(saved);
    }

    private void loadValues(MapDataHolder data) {
        this.strings.clear();
        this.stringLists.clear();

        data.forEach((k, v) -> {
            if (v instanceof ListDataHolder listObject) {
                List<String> list = new ArrayList<>();

                listObject.forEach(entry -> {
                    if (entry instanceof StringDataHolder primitive) {
                        list.add(primitive.get());
                    }
                });

                this.stringLists.put(k, list);
            } else if (v instanceof StringDataHolder primitive) {
                this.strings.put(k, primitive.get());
            }
        });
    }

    private MapDataHolder readSaved(JsonReader source) {
        if (this.file.exists()) {
            try {
                return source.readFile(this.file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return MapDataHolder.empty("");
    }

    private MapDataHolder readInternal(JsonReader source) {
        try (InputStream stream = Plugin.getInstance().getResource(this.path)) {
            return source.readStream(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return MapDataHolder.empty("");
    }
}

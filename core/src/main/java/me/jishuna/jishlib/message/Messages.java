package me.jishuna.jishlib.message;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import me.jishuna.jishlib.Constants;
import me.jishuna.jishlib.Plugin;
import me.jishuna.jishlib.data.object.ListDataObject;
import me.jishuna.jishlib.data.object.MapDataObject;
import me.jishuna.jishlib.data.object.PrimitiveDataObject;
import me.jishuna.jishlib.data.source.JsonDataSource;

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
        JsonDataSource source = new JsonDataSource(null);

        MapDataObject saved = readSaved(source);
        MapDataObject internal = readInternal(source);

        saved.merge(internal);
        source.write(saved, this.file);

        loadValues(saved);
    }

    private void loadValues(MapDataObject data) {
        this.strings.clear();
        this.stringLists.clear();

        data.forEach((k, v) -> {
            if (v instanceof ListDataObject listObject) {
                List<String> list = new ArrayList<>();

                listObject.forEach(entry -> {
                    if (entry instanceof PrimitiveDataObject primitive) {
                        list.add(primitive.asString());
                    }
                });

                this.stringLists.put(k, list);
            } else if (v instanceof PrimitiveDataObject primitive) {
                this.strings.put(k, primitive.asString());
            }
        });
    }

    private MapDataObject readSaved(JsonDataSource source) {
        if (this.file.exists()) {
            return source.read(this.file);
        }

        return MapDataObject.empty();
    }

    private MapDataObject readInternal(JsonDataSource source) {
        try (InputStream stream = Plugin.getInstance().getResource(this.path);
                Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            return source.read(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return MapDataObject.empty();
    }
}

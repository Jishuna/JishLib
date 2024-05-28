package me.jishuna.jishlib.message;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import me.jishuna.jishlib.Constants;
import me.jishuna.jishlib.data.object.DataObject;
import me.jishuna.jishlib.data.object.ListDataObject;
import me.jishuna.jishlib.data.object.MapDataObject;
import me.jishuna.jishlib.data.object.PrimitiveDataObject;
import me.jishuna.jishlib.data.source.DataSources;

public class Messages {
    private static Messages INSTANCE;

    public static void initialize(File file) {
        if (INSTANCE != null) {
            throw new IllegalStateException("Messages already initialized");
        }

        INSTANCE = new Messages(file);
        INSTANCE.loadAll();
    }

    public static void reload() {
        INSTANCE.loadAll();
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

    private final File file;
    private final Map<String, String> strings = new ConcurrentHashMap<>();
    private final Map<String, List<String>> stringLists = new ConcurrentHashMap<>();

    private Messages(File file) {
        this.file = file;
    }

    private void loadAll() {
        MapDataObject data = DataSources.YAML.read(this.file);
        data.forEach((k, v) -> parseRecursive(k, v));
    }

    private void parseRecursive(String key, DataObject<?> value) {
        if (value instanceof MapDataObject mapObject) {
            mapObject.forEach((k, v) -> parseRecursive(key + "." + k, v));
            return;
        }

        if (value instanceof ListDataObject listObject) {
            List<String> list = new ArrayList<>();

            listObject.forEach(entry -> {
                if (entry instanceof PrimitiveDataObject primitive) {
                    list.add(primitive.asString());
                }
            });

            this.stringLists.put(key, list);
            return;
        }

        if (value instanceof PrimitiveDataObject primitive) {
            this.strings.put(key, primitive.asString());
        }
    }
}

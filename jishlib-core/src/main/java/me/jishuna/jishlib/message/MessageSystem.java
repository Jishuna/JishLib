package me.jishuna.jishlib.message;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.yaml.snakeyaml.nodes.AnchorNode;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import me.jishuna.jishlib.JishLib;
import me.jishuna.jishlib.util.StringUtils;

public final class MessageSystem {
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("%\\S*%");

    private static MessageSystem INSTANCE;
    private static String fileName;

    public static void initialize(String file) {
        fileName = file;
        reload();
    }

    private static MessageSystem getInstance() {
        return INSTANCE;
    }

    public static void reload() {
        File folder = JishLib.getPlugin().getDataFolder();

        if (!folder.exists() && !folder.mkdirs()) {
            JishLib.getLogger().severe("Failed to load messages: Failed to create message file");
            return;
        }

        INSTANCE = new MessageSystem(fileName);
    }

    public static String get(String key) {
        return getInstance().strings.getOrDefault(key, key);
    }

    public static String get(String key, Map<String, Supplier<Object>> placeholders) {
        return replacePlaceholders(getInstance().strings.getOrDefault(key, key), placeholders);
    }

    public static List<String> getList(String key) {
        return getInstance().lists.getOrDefault(key, Collections.emptyList());
    }

    public static List<String> getList(String key, Map<String, Supplier<Object>> placeholders) {
        List<String> list = getInstance().lists.getOrDefault(key, Collections.emptyList());
        return list.stream().map(string -> replacePlaceholders(string, placeholders)).toList();
    }

    private static String replacePlaceholders(String string, Map<String, Supplier<Object>> placeholders) {
        if (placeholders.isEmpty()) {
            return string;
        }

        Matcher matcher = PLACEHOLDER_PATTERN.matcher(string);

        return matcher.replaceAll(match -> {
            String key = match.group();
            key = key.substring(1, key.length() - 1);
            Supplier<Object> replacement = placeholders.get(key);

            return replacement == null ? "[Invalid Placeholder: " + key + "]" : Objects.toString(replacement.get());
        });
    }

    public static void printAll() {
        getInstance().strings.forEach((k, v) -> Bukkit.getConsoleSender().sendMessage(k + ": " + v));
        getInstance().lists.forEach((k, v) -> {
            Bukkit.getConsoleSender().sendMessage(k + ":");
            v.forEach(s -> Bukkit.getConsoleSender().sendMessage(" - " + s));
        });
    }

    private final Map<String, List<String>> lists = new HashMap<>();
    private final Map<String, String> strings = new HashMap<>();

    private MessageSystem(String fileName) {
        MessageLoader loader = new MessageLoader(fileName);
        read(loader.load(), this.strings, this.lists);
    }

    private void read(MappingNode data, Map<String, String> strings, Map<String, List<String>> lists) {
        for (NodeTuple tuple : data.getValue()) {
            Node key = tuple.getKeyNode();
            String keyString = String.valueOf(MessageLoader.CONSTRUCTOR.construct(key));
            Node value = tuple.getValueNode();

            while (value instanceof AnchorNode anchor) {
                value = anchor.getRealNode();
            }

            Object object = MessageLoader.CONSTRUCTOR.construct(value);
            if (object instanceof List<?> list) {
                lists.put(keyString, list.stream().map(this::convertObject).toList());
            } else {
                strings.put(keyString, convertObject(object));
            }
        }
    }

    private String convertObject(Object value) {
        if (value == null) {
            return "";
        }

        return StringUtils.miniMessageToLegacy(value.toString());
    }
}

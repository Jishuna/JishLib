package me.jishuna.jishlib.message;

import com.google.common.base.Preconditions;
import java.io.File;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import me.jishuna.jishlib.JishLib;
import me.jishuna.jishlib.util.StringUtils;

public final class MessageAPI {
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("%\\S*%");

    private static MessageHandler manager;
    private static String fileName;

    public static void initialize(String file) {
        Preconditions.checkArgument(manager == null, "MessageAPI already initialized!");
        Preconditions.checkArgument(JishLib.isInitialized(), "JishLib must be initialized first!");

        fileName = file;
        reload();
    }

    public static void reload() {
        File folder = JishLib.getPlugin().getDataFolder();

        if (!folder.exists() && !folder.mkdirs()) {
            JishLib.getLogger().severe("Failed to load messages: Failed to create message file");
            return;
        }

        MessageLoader loader = new MessageLoader(fileName);
        loadFrom(loader.load());
    }

    private static void loadFrom(Map<String, Object> data) {
        Map<String, String> strings = new HashMap<>();
        Map<String, List<String>> lists = new HashMap<>();

        readRecursivly(data, strings, lists);

        manager = new MessageHandler(strings, lists);
    }

    private static void readRecursivly(Map<?, ?> data, Map<String, String> strings, Map<String, List<String>> lists) {
        for (Entry<?, ?> entry : data.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();

            if (value instanceof Map<?, ?> map) {
                readRecursivly(map, strings, lists);
            } else if (value instanceof List<?> list) {
                lists.put(key, list.stream().map(MessageAPI::convertObject).toList());
            } else if (value != null) {
                strings.put(key, convertObject(value));
            }
        }
    }

    private static String convertObject(Object value) {
        if (value == null) {
            return "";
        }

        return StringUtils.miniMessageToLegacy(value.toString());
    }

    public static String get(String key) {
        return getInstance().messages.getOrDefault(key, key);
    }

    public static String getLegacy(String key, Object... format) {
        return MessageFormat.format(getInstance().messages.getOrDefault(key, key), format);
    }

    public static String get(String key, Map<String, Supplier<Object>> placeholders) {
        return replacePlaceholders(getInstance().messages.getOrDefault(key, key), placeholders);
    }

    public static List<String> getList(String key) {
        return getInstance().lists.getOrDefault(key, Collections.emptyList());
    }

    public static List<String> getListLegacy(String key, Object... format) {
        List<String> list = getInstance().lists.getOrDefault(key, Collections.emptyList());
        return list.stream().map(string -> MessageFormat.format(string, format)).toList();
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

    private static MessageHandler getInstance() {
        if (manager == null) {
            throw new IllegalStateException("MessageAPI not initialized!");
        }
        return manager;
    }

    public static void printAll() {
        getInstance().messages.forEach((k, v) -> Bukkit.getConsoleSender().sendMessage(k + ": " + v));
        getInstance().lists.forEach((k, v) -> {
            Bukkit.getConsoleSender().sendMessage(k + ":");
            v.forEach(s -> Bukkit.getConsoleSender().sendMessage(" - " + s));
        });
    }

    private MessageAPI() {
    }

    static final class MessageHandler {
        private final Map<String, List<String>> lists;
        private final Map<String, String> messages;

        public MessageHandler(Map<String, String> messages, Map<String, List<String>> lists) {
            this.messages = messages;
            this.lists = lists;
        }
    }
}

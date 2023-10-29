package me.jishuna.jishlib.message;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Messages {
    private static Messages instance;

    private final Map<String, String> messages;
    private final Map<String, List<String>> lists;

    public Messages(Map<String, String> messages, Map<String, List<String>> lists) {
        this.messages = messages;
        this.lists = lists;
    }

    public String getString(String key) {
        return this.messages.getOrDefault(key, key);
    }

    public List<String> getStringList(String key) {
        return this.lists.getOrDefault(key, Collections.emptyList());
    }

    public static void initialize(MessageParser parser) {
        Map<String, String> messages = new HashMap<>();
        Map<String, List<String>> lists = new HashMap<>();

        for (Entry<String, MessageEntry> entry : parser.getRawMessages().entrySet()) {
            MessageEntry messageEntry = entry.getValue();
            if (messageEntry instanceof SingleMessageEntry single) {
                messages.put(entry.getKey(), single.getValue());
            } else if (messageEntry instanceof ListMessageEntry list) {
                lists.put(entry.getKey(), list.getValue());
            }
        }

        instance = new Messages(messages, lists);
    }

    public static String get(String key) {
        return instance.getString(key);
    }

    public static String get(String key, Object... format) {
        return MessageFormat.format(instance.getString(key), format);
    }

    public static List<String> getList(String key) {
        return instance.getStringList(key);
    }

    public static List<String> getList(String key, Object... format) {
        List<String> list = instance.getStringList(key);
        return list.stream().map(string -> MessageFormat.format(string, format)).toList();
    }
}

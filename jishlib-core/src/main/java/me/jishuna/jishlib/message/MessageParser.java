package me.jishuna.jishlib.message;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageParser {
    private static final Pattern LIST_PATTERN = Pattern.compile("\"(.*?[^\\\\])\"");

    private final Map<String, MessageEntry> messages = new LinkedHashMap<>();

    private MessageParser() {
    }

    public static MessageParser empty() {
        return new MessageParser();
    }

    public static MessageParser parse(List<MessageEntry> messages) {
        MessageParser parser = new MessageParser();
        parser.parseMessages(messages);

        return parser;
    }

    public Map<String, MessageEntry> getRawMessages() {
        return this.messages;
    }

    public boolean merge(MessageParser other) {
        boolean added = false;

        for (Entry<String, MessageEntry> entry : other.messages.entrySet()) {
            String key = entry.getKey();
            if (!this.messages.containsKey(key)) {
                this.messages.put(key, entry.getValue());
                added = true;
            }
        }

        return added;
    }

    private List<String> parseListMessage(String input) {
        Matcher matcher = LIST_PATTERN.matcher(input);
        return matcher.results().map(this::processMatch).toList();
    }

    private void parseMessage(MessageEntry entry) {
        String line = entry.getLine();
        int splitIndex = line.indexOf(':');
        if (splitIndex < 0) {
            return;
        }

        String key = line.substring(0, splitIndex);
        String message = line.substring(splitIndex + 1).trim();
        MessageEntry parsed;
        if (message.startsWith("[") && message.endsWith("]")) {
            parsed = new ListMessageEntry(entry, parseListMessage(message));
        } else {
            parsed = new SingleMessageEntry(entry, message);
        }

        this.messages.put(key, parsed);
    }

    private void parseMessages(List<MessageEntry> messages) {
        for (MessageEntry entry : messages) {
            parseMessage(entry);
        }
    }

    private String processMatch(MatchResult result) {
        return result.group(1).replace("\\\"", "\"");
    }
}

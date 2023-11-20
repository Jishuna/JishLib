package me.jishuna.jishlib.message;

import com.google.common.base.Preconditions;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import me.jishuna.jishlib.JishLib;
import me.jishuna.jishlib.util.StringUtils;

public final class MessageAPI {
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

        try {
            if (!folder.exists() && !folder.mkdirs()) {
                JishLib.getLogger().severe("Failed to load messages: Failed to create message file");
                return;
            }
            MessageParser parser = MessageParser.empty();
            File target = new File(folder, fileName);

            if (target.exists()) {
                MessageReader external = new MessageReader();
                parser.merge(MessageParser.parse(external.readMessages(new FileInputStream(target))));
            } else if (!target.createNewFile()) {
                JishLib.getLogger().severe("Failed to load messages: Failed to create message file");
                return;
            }

            MessageReader internal = new MessageReader();
            if (parser.merge(MessageParser.parse(internal.readMessages(JishLib.getPlugin().getResource(fileName))))) {
                MessageWriter.writeMessages(parser.getRawMessages().values(), new FileOutputStream(target));
            }

            loadFrom(parser);
        } catch (IOException e) {
            JishLib.getLogger().log(Level.SEVERE, "Failed to load messages: {0}", e.getMessage());
            e.printStackTrace();
        }
    }

    private static void loadFrom(MessageParser parser) {
        Map<String, String> messages = new HashMap<>();
        Map<String, List<String>> lists = new HashMap<>();

        for (Entry<String, MessageEntry> entry : parser.getRawMessages().entrySet()) {
            MessageEntry messageEntry = entry.getValue();
            if (messageEntry instanceof SingleMessageEntry single) {
                messages.put(entry.getKey(), StringUtils.miniMessageToLegacy(single.getValue()));
            } else if (messageEntry instanceof ListMessageEntry list) {
                lists.put(entry.getKey(), list.getValue().stream().map(StringUtils::miniMessageToLegacy).toList());
            }
        }

        manager = new MessageHandler(messages, lists);
    }

    public static String get(String key) {
        return getInstance().messages.getOrDefault(key, key);
    }

    public static String get(String key, Object... format) {
        return MessageFormat.format(getInstance().messages.getOrDefault(key, key), format);
    }

    public static List<String> getList(String key) {
        return getInstance().lists.getOrDefault(key, Collections.emptyList());
    }

    public static List<String> getList(String key, Object... format) {
        List<String> list = getInstance().lists.getOrDefault(key, Collections.emptyList());
        return list.stream().map(string -> MessageFormat.format(string, format)).toList();
    }

    private static MessageHandler getInstance() {
        if (manager == null) {
            throw new IllegalStateException("MessageAPI not initialized!");
        }
        return manager;
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

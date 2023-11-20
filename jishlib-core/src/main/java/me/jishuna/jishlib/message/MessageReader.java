package me.jishuna.jishlib.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MessageReader {
    private final List<String> commentBuffer = new ArrayList<>();
    private final List<MessageEntry> messages = new ArrayList<>();

    public List<MessageEntry> readMessages(InputStream stream) {
        if (stream == null) {
            return this.messages;
        }

        return readMessages(new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)));
    }

    public List<MessageEntry> readMessages(BufferedReader reader) {
        if (reader == null) {
            return this.messages;
        }

        try (reader) {
            while (reader.ready()) {
                processLine(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.messages;
    }

    private void processLine(String line) {
        line = line.trim();

        if (line.isBlank() || line.startsWith("#")) {
            this.commentBuffer.add(line);
            return;
        }

        this.messages.add(new MessageEntry(line, this.commentBuffer.toArray(String[]::new)));
        this.commentBuffer.clear();
    }
}

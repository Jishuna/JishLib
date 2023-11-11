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
    private final InputStream stream;

    public MessageReader(InputStream stream) {
        this.stream = stream;
    }

    public List<MessageEntry> readMessages() {
        if (this.stream == null) {
            return this.messages;
        }

        try (this.stream; BufferedReader reader = new BufferedReader(new InputStreamReader(this.stream, StandardCharsets.UTF_8))) {
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

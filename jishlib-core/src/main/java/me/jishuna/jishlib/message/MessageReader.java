package me.jishuna.jishlib.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MessageReader {
    private final InputStream stream;

    private final List<String> commentBuffer = new ArrayList<>();
    private final List<MessageEntry> messages = new ArrayList<>();

    public MessageReader(InputStream stream) {
        this.stream = stream;
    }

    public List<MessageEntry> readMessages() {
        if (this.stream == null) {
            return this.messages;
        }

        try (stream; BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
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
            commentBuffer.add(line);
            return;
        }

        messages.add(new MessageEntry(line, this.commentBuffer.toArray(String[]::new)));
        this.commentBuffer.clear();
    }
}

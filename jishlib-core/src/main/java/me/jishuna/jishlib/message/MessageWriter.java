package me.jishuna.jishlib.message;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class MessageWriter {
    private final OutputStream stream;

    public MessageWriter(OutputStream stream) {
        this.stream = stream;
    }

    public void writeMessages(Collection<? extends MessageEntry> messages) {
        if (this.stream == null) {
            return;
        }

        try (this.stream; BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.stream, StandardCharsets.UTF_8))) {
            for (MessageEntry entry : messages) {
                entry.write(writer);
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

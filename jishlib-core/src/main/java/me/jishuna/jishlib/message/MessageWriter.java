package me.jishuna.jishlib.message;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class MessageWriter {
    public static void writeMessages(Collection<? extends MessageEntry> messages, OutputStream stream) {
        writeMessages(messages, new BufferedWriter(new OutputStreamWriter(stream, StandardCharsets.UTF_8)));
    }

    public static void writeMessages(Collection<? extends MessageEntry> messages, BufferedWriter writer) {
        if (writer == null) {
            return;
        }

        try (writer) {
            for (MessageEntry entry : messages) {
                entry.write(writer);
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

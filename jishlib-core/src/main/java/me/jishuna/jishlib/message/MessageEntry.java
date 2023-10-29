package me.jishuna.jishlib.message;

import java.io.BufferedWriter;
import java.io.IOException;

public class MessageEntry {
    private final String line;
    private final String[] comments;

    public MessageEntry(String line, String[] comments) {
        this.line = line;
        this.comments = comments;
    }

    public String getLine() {
        return line;
    }

    public String[] getComments() {
        return this.comments;
    }

    public void write(BufferedWriter writer) throws IOException {
        for (String comment : comments) {
            writer.write(comment);
            writer.newLine();
        }

        writer.write(line);
        writer.newLine();
    }
}

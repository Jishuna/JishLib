package me.jishuna.jishlib.message;

import java.io.BufferedWriter;
import java.io.IOException;

public class MessageEntry {
    private final String[] comments;
    private final String line;

    public MessageEntry(String line, String[] comments) {
        this.line = line;
        this.comments = comments;
    }

    public String[] getComments() {
        return this.comments;
    }

    public String getLine() {
        return this.line;
    }

    public void write(BufferedWriter writer) throws IOException {
        for (String comment : this.comments) {
            writer.write(comment);
            writer.newLine();
        }

        writer.write(this.line);
        writer.newLine();
    }
}

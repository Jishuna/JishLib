package me.jishuna.jishlib.message;

import java.util.List;

public class ListMessageEntry extends MessageEntry {
    private final List<String> value;

    public ListMessageEntry(MessageEntry entry, List<String> value) {
        super(entry.getLine(), entry.getComments());
        this.value = value;
    }

    public List<String> getValue() {
        return value;
    }
}

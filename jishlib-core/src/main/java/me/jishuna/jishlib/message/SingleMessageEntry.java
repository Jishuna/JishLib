package me.jishuna.jishlib.message;

public class SingleMessageEntry extends MessageEntry {
    private final String value;

    public SingleMessageEntry(MessageEntry entry, String value) {
        super(entry.getLine(), entry.getComments());
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

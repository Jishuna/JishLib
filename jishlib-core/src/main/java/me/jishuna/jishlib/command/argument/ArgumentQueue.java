package me.jishuna.jishlib.command.argument;

import java.util.ArrayDeque;
import java.util.Arrays;
import me.jishuna.jishlib.command.CommandException;

public class ArgumentQueue extends ArrayDeque<String> {
    private static final long serialVersionUID = 1L;

    public ArgumentQueue(String[] args) {
        super(Arrays.asList(args));
    }

    public <T> T pollAs(Class<T> clazz) {
        ArgumentParser<T> parser = ArgumentParsers.getParser(clazz);
        if (isEmpty()) {
            throw new CommandException("TODO");
        }

        String raw = poll();
        T value = parser.parse(raw);
        if (value == null) {
            throw new CommandException("TODO");
        }

        return value;
    }

    @Override
    public String peekFirst() {
        String s = super.peekFirst();
        return s == null ? "none" : s;
    }

    @Override
    public String pollFirst() {
        String s = super.pollFirst();
        return s == null ? "none" : s;
    }
}

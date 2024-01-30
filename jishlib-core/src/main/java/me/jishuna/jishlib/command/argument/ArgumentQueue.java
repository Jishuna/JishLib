package me.jishuna.jishlib.command.argument;

import java.util.ArrayDeque;
import java.util.Arrays;
import me.jishuna.jishlib.command.CommandException;
import me.jishuna.jishlib.message.MessageAPI;

public class ArgumentQueue extends ArrayDeque<String> {
    private static final long serialVersionUID = 1L;

    public ArgumentQueue(String[] args) {
        super(Arrays.asList(args));
    }

    public <T> T pollAs(Class<T> clazz) {
        ArgumentParser<T> parser = ArgumentParsers.getParser(clazz);
        if (isEmpty()) {
            throw new CommandException(MessageAPI.getLegacy("command.invalid-arg", "none", parser.getValidArguments()));
        }

        String raw = poll();
        T value = ArgumentParsers.getParser(clazz).parse(raw);
        if (value == null) {
            throw new CommandException(MessageAPI.getLegacy("command.invalid-arg", raw, parser.getValidArguments()));
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

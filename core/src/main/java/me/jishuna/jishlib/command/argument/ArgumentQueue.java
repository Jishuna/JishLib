package me.jishuna.jishlib.command.argument;

import java.util.ArrayDeque;
import java.util.Arrays;
import org.bukkit.command.CommandException;
import me.jishuna.jishlib.message.Messages;
import me.jishuna.jishlib.util.Components;

public class ArgumentQueue extends ArrayDeque<String> {
    private static final long serialVersionUID = 1L;

    public ArgumentQueue(String[] args) {
        super(Arrays.asList(args));
    }

    public <T> T pollAs(Class<T> clazz) {
        ArgumentParser<T> parser = ArgumentParsers.getParser(clazz);
        if (isEmpty()) {
            throw new CommandException(Components.toString(Messages.get("command.invalid-arg")));
        }

        String raw = poll();
        T value = parser.parse(raw);
        if (value == null) {
            throw new CommandException(Components.toString(Messages.get("command.invalid-arg")));
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

package me.jishuna.jishlib.command.argument;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import me.jishuna.jishlib.command.CommandException;
import me.jishuna.jishlib.message.Messages;

public class ArgumentQueue {
    private Deque<String> internalQueue;

    public ArgumentQueue(String[] args) {
        this.internalQueue = new ArrayDeque<>(Arrays.asList(args));
    }

    public void dropFirst() {
        this.internalQueue.poll();
    }

    public <T> T poll(Class<T> clazz) {
        ArgumentParser<T> parser = ArgumentParsers.getParser(clazz);
        if (this.internalQueue.isEmpty()) {
            throw new CommandException(Messages
                    .get("command.invalid-arg",
                            Placeholder.unparsed("input", "None"),
                            Placeholder.unparsed("args", parser.getValidArguments())));
        }

        String raw = this.internalQueue.poll();
        T value = parser.parse(raw);
        if (value == null) {
            throw new CommandException(Messages
                    .get("command.invalid-arg",
                            Placeholder.unparsed("input", raw),
                            Placeholder.unparsed("args", parser.getValidArguments())));
        }

        return value;
    }

    public String peek() {
        String s = this.internalQueue.peekFirst();
        return s == null ? "None" : s;
    }

    public boolean isEmpty() {
        return this.internalQueue.isEmpty();
    }

    public int size() {
        return this.internalQueue.size();
    }
}

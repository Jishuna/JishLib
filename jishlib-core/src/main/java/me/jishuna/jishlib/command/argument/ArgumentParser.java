package me.jishuna.jishlib.command.argument;

import java.util.List;

public interface ArgumentParser<T> {

    public T parse(String string);

    public List<String> getSuggestions(String input);

    public default String getValidArguments() {
        return String.join(", ", getSuggestions(""));
    }
}

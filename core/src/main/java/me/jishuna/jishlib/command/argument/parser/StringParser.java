package me.jishuna.jishlib.command.argument.parser;

import java.util.Collections;
import java.util.List;
import me.jishuna.jishlib.command.argument.ArgumentParser;

public class StringParser implements ArgumentParser<String> {

    @Override
    public String parse(String string) {
        return string;
    }

    @Override
    public List<String> getSuggestions(String input) {
        return Collections.emptyList();
    }

    @Override
    public String getValidArguments() {
        return "any";
    }
}

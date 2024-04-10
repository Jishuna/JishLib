package me.jishuna.jishlib.command.argument.parser;

import java.util.Collections;
import java.util.List;
import org.bukkit.NamespacedKey;
import me.jishuna.jishlib.command.argument.ArgumentParser;

public class NamespacedKeyParser implements ArgumentParser<NamespacedKey> {

    @Override
    public NamespacedKey parse(String string) {
        return NamespacedKey.fromString(string);
    }

    @Override
    public List<String> getSuggestions(String input) {
        return Collections.emptyList();
    }
}

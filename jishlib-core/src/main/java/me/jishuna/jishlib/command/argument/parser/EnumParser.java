package me.jishuna.jishlib.command.argument.parser;

import com.google.common.base.Enums;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.util.StringUtil;
import me.jishuna.jishlib.command.argument.ArgumentParser;

@SuppressWarnings("rawtypes")
public class EnumParser<T extends Enum> implements ArgumentParser<T> {
    private final Class<T> clazz;
    private final List<String> options;

    @SuppressWarnings("unchecked")
    public EnumParser(Class<?> clazz) {
        this.clazz = (Class<T>) clazz;
        this.options = Arrays.stream(clazz.getEnumConstants()).map(e -> e.toString().toLowerCase()).toList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T parse(String string) {
        return (T) Enums.getIfPresent(this.clazz, string.toUpperCase()).orNull();
    }

    @Override
    public List<String> getSuggestions(String input) {
        return StringUtil.copyPartialMatches(input, this.options, new ArrayList<>());
    }
}

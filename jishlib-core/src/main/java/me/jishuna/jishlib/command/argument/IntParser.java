package me.jishuna.jishlib.command.argument;

import com.google.common.primitives.Ints;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.bukkit.util.StringUtil;

public class IntParser implements ArgumentParser<Integer> {
    private final List<String> numbers = IntStream.range(1, 11).mapToObj(Integer::toString).toList();

    @Override
    public Integer parse(String string) {
        return Ints.tryParse(string);
    }

    @Override
    public List<String> getSuggestions(String input) {
        return StringUtil.copyPartialMatches(input, this.numbers, new ArrayList<>());
    }

    @Override
    public String getValidArguments() {
        return "any integer";
    }
}

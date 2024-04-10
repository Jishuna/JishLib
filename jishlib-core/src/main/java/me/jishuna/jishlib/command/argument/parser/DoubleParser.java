package me.jishuna.jishlib.command.argument.parser;

import com.google.common.primitives.Doubles;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.bukkit.util.StringUtil;
import me.jishuna.jishlib.command.argument.ArgumentParser;

public class DoubleParser implements ArgumentParser<Double> {
    private final List<String> numbers = IntStream.range(1, 11).mapToObj(Integer::toString).toList();

    @Override
    public Double parse(String string) {
        return Doubles.tryParse(string);
    }

    @Override
    public List<String> getSuggestions(String input) {
        return StringUtil.copyPartialMatches(input, this.numbers, new ArrayList<>());
    }

    @Override
    public String getValidArguments() {
        return "any number";
    }
}

package me.jishuna.jishlib.command.argument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.util.StringUtil;

public class OfflinePlayerParser implements ArgumentParser<OfflinePlayer> {
    private final List<String> cache = Arrays
            .stream(Bukkit.getOfflinePlayers())
            .map(OfflinePlayer::getName)
            .filter(Objects::nonNull)
            .toList();

    @SuppressWarnings("deprecation")
    @Override
    public OfflinePlayer parse(String string) {
        return Bukkit.getOfflinePlayer(string);
    }

    @Override
    public List<String> getSuggestions(String input) {
        return StringUtil.copyPartialMatches(input, this.cache, new ArrayList<>());
    }

    @Override
    public String getValidArguments() {
        return "any value";
    }
}

package me.jishuna.jishlib.command.argument;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class PlayerParser implements ArgumentParser<Player> {

    @Override
    public Player parse(String string) {
        return Bukkit.getPlayer(string);
    }

    @Override
    public List<String> getSuggestions(String input) {
        return StringUtil.copyPartialMatches(input, Bukkit.getOnlinePlayers().stream().map(Player::getName).toList(), new ArrayList<>());
    }
}

package me.jishuna.jishlib.command.argument;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public final class ArgumentParsers {
    private static final Map<Class<?>, ArgumentParser<?>> parsers = new HashMap<>();

    public static final ArgumentParser<Player> PLAYER = register(Player.class, new PlayerParser());
    public static final ArgumentParser<OfflinePlayer> OFFLINE_PLAYER = register(OfflinePlayer.class, new OfflinePlayerParser());
    public static final ArgumentParser<Integer> INTEGER = register(int.class, new IntParser());

    @SuppressWarnings("unchecked")
    public static <T> ArgumentParser<T> getParser(Class<T> clazz) {
        return (ArgumentParser<T>) parsers.get(clazz);
    }

    private static <T> ArgumentParser<T> register(Class<T> clazz, ArgumentParser<T> parser) {
        parsers.put(clazz, parser);
        return parser;
    }

    private ArgumentParsers() {
    }
}

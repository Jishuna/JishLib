package me.jishuna.jishlib.command.argument;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import me.jishuna.jishlib.command.argument.parser.DoubleParser;
import me.jishuna.jishlib.command.argument.parser.EnumParser;
import me.jishuna.jishlib.command.argument.parser.IntParser;
import me.jishuna.jishlib.command.argument.parser.NamespacedKeyParser;
import me.jishuna.jishlib.command.argument.parser.OfflinePlayerParser;
import me.jishuna.jishlib.command.argument.parser.PlayerParser;

public final class ArgumentParsers {
    private static final Map<Class<?>, ArgumentParser<?>> parsers = new HashMap<>();

    public static final ArgumentParser<Player> PLAYER = register(Player.class, new PlayerParser());
    public static final ArgumentParser<OfflinePlayer> OFFLINE_PLAYER = register(OfflinePlayer.class, new OfflinePlayerParser());
    public static final ArgumentParser<NamespacedKey> NAMESPACED_KEY = register(NamespacedKey.class, new NamespacedKeyParser());
    public static final ArgumentParser<Integer> INTEGER = register(int.class, new IntParser());
    public static final ArgumentParser<Double> DOUBLE = register(double.class, new DoubleParser());

    @SuppressWarnings("unchecked")
    public static <T> ArgumentParser<T> getParser(Class<T> clazz) {
        if (clazz.isEnum()) {
            return (ArgumentParser<T>) new EnumParser<>(clazz);
        }

        return (ArgumentParser<T>) parsers.get(clazz);
    }

    private static <T> ArgumentParser<T> register(Class<T> clazz, ArgumentParser<T> parser) {
        parsers.put(clazz, parser);
        return parser;
    }

    private ArgumentParsers() {
    }
}

package me.jishuna.jishlib.pdc;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import org.bukkit.NamespacedKey;

public final class KeyHelper {
    private static final Map<String, NamespacedKey> VALUE_KEYS = new HashMap<>();

    static {
        IntStream.range(0, 100).forEach(KeyHelper::getValueKey);
    }

    public static NamespacedKey getValueKey(final int index) {
        return getValueKey(String.valueOf(index));
    }

    public static NamespacedKey getValueKey(final String name) {
        return VALUE_KEYS.computeIfAbsent(name, k -> NamespacedKey.fromString("v:" + name));
    }

    private KeyHelper() {
    }
}

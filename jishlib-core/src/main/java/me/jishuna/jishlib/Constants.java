package me.jishuna.jishlib;

import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

/**
 * Various static constants.
 */
public class Constants {
    /**
     * Static {@link MiniMessage} instance.
     */
    public static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    /**
     * Static {@link LegacyComponentSerializer} instance.
     */
    public static final LegacyComponentSerializer LEGACY_SERIALIZER = BukkitComponentSerializer.legacy();

    private Constants() {
    }
}

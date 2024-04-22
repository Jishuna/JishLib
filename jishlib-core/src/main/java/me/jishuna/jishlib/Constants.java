package me.jishuna.jishlib;

import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public final class Constants {
    public static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    public static final LegacyComponentSerializer LEGACY_SERIALIZER = BukkitComponentSerializer.legacy();

    private Constants() {
    }
}

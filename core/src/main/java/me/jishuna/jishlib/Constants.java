package me.jishuna.jishlib;

import net.kyori.adventure.platform.bukkit.MinecraftComponentSerializer;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public final class Constants {
    public static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    public static final MinecraftComponentSerializer MOJANG_SERIALIZER = MinecraftComponentSerializer.get();
    public static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer
            .builder()
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();

    private Constants() {
    }
}

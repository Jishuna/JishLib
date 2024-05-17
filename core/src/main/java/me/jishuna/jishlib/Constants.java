package me.jishuna.jishlib;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public final class Constants {
    public static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    public static final GsonComponentSerializer GSON_SERIALIZER = GsonComponentSerializer.gson();
    public static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer
            .builder()
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();

    private Constants() {
    }
}

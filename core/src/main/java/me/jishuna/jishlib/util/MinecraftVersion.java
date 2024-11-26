package me.jishuna.jishlib.util;

import org.bukkit.Bukkit;

public final class MinecraftVersion {
    public static final SemanticVersion CURRENT_VERSION = SemanticVersion.fromString(getServerVersion());

    public static final SemanticVersion MC1_21_3 = new SemanticVersion(1, 21, 3);

    private static String getServerVersion() {
        String version = Bukkit.getServer().getBukkitVersion();
        if (version.contains("-")) {
            return version.substring(0, version.indexOf('-'));
        }

        return version;
    }

    private MinecraftVersion() {
    }
}

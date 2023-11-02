package me.jishuna.jishlib.util;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Server;

public class Utils {
    private Utils() {
    }

    public static long getChunkKey(Chunk chunk) {
        return ((long) chunk.getZ() << 32) | (chunk.getX() & 0xFFFFFFFFL);
    }

    public static String getOnlineMode() {
        Server server = Bukkit.getServer();
        if (server.getOnlineMode()) {
            return "Online";
        }

        if (server.spigot().getConfig().getBoolean("settings.bungeecord", false)) {
            return "Bungee";
        }

        return "Offline";
    }
}

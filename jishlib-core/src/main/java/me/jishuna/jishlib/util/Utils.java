package me.jishuna.jishlib.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Server;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

public class Utils {
    private static final String TEXTURE_URL = "http://textures.minecraft.net/texture/";

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

    public static PlayerProfile createProfile(String texture) {
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.nameUUIDFromBytes(texture.getBytes()), "Custom");
        PlayerTextures textures = profile.getTextures();
        try {
            textures.setSkin(new URL(TEXTURE_URL + texture));
        } catch (MalformedURLException ignored) {
            return null;
        }

        profile.setTextures(textures);
        return profile;
    }
}

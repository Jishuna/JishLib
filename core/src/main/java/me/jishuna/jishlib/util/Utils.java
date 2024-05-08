package me.jishuna.jishlib.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

public class Utils {
    private static final String TEXTURE_URL = "http://textures.minecraft.net/texture/";

    public static long getChunkKey(Chunk chunk) {
        return ((long) chunk.getZ() << 32) | (chunk.getX() & 0xFFFFFFFFL);
    }

    public static long getChunkKey(int x, int z) {
        return ((long) z << 32) | (x & 0xFFFFFFFFL);
    }

    public static PlayerProfile createProfile(String url) {
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.nameUUIDFromBytes(url.getBytes()), "Custom");
        PlayerTextures textures = profile.getTextures();
        try {
            textures.setSkin(new URI(TEXTURE_URL + url).toURL());
        } catch (URISyntaxException | MalformedURLException e) {
            return null;
        }

        profile.setTextures(textures);
        return profile;
    }

    private Utils() {
    }
}

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

    /**
     * Gets a unique long representing the coordinates of a chunk.
     *
     * @param chunk the chunk
     * @return a unique long representing the coordinates of the chunk
     */
    public static long getChunkKey(Chunk chunk) {
        return ((long) chunk.getZ() << 32) | (chunk.getX() & 0xFFFFFFFFL);
    }

    public static long getChunkKey(int x, int z) {
        return ((long) z << 32) | (x & 0xFFFFFFFFL);
    }

    /**
     * Gets the servers {@link ServerMode}.
     *
     * @return the servers {@link ServerMode}
     */
    public static ServerMode getServerMode() {
        Server server = Bukkit.getServer();
        if (server.getOnlineMode()) {
            return ServerMode.ONLINE;
        }

        if (server.spigot().getConfig().getBoolean("settings.bungeecord", false)) {
            return ServerMode.BUNGEE;
        }

        return ServerMode.OFFLINE;
    }

    /**
     * Creates a {@link PlayerProfile} with the given texture URL. <br>
     * {@code http://textures.minecraft.net/texture} prefix is optional.
     *
     * @param url the texture URL
     * @return a {@link PlayerProfile} for the given URL, or null if the URL is
     *         invalid
     */
    public static PlayerProfile createProfile(String url) {
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.nameUUIDFromBytes(url.getBytes()), "Custom");
        PlayerTextures textures = profile.getTextures();
        try {
            textures.setSkin(new URL(TEXTURE_URL + url));
        } catch (MalformedURLException e) {
            return null;
        }

        profile.setTextures(textures);
        return profile;
    }

    private Utils() {
    }

    /**
     * An enum representing the possible modes of a server.
     */
    public enum ServerMode {
        /**
         * Online mode.
         */
        ONLINE("Online"),
        /**
         * Offline mode.
         */
        OFFLINE("Offline"),
        /**
         * Offline mode but with BungeeCord enabled.
         */
        BUNGEE("Bungee");

        private final String name;

        private ServerMode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}

package me.jishuna.jishlib.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

public class Utils {
    private static final String TEXTURE_URL = "http://textures.minecraft.net/texture/";

    public static PlayerProfile createProfile(String url) {
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.nameUUIDFromBytes(url.getBytes()), "custom");
        PlayerTextures textures = profile.getTextures();
        try {
            textures.setSkin(new URI(TEXTURE_URL + url).toURL());
        } catch (URISyntaxException | MalformedURLException e) {
            return null;
        }

        profile.setTextures(textures);
        return profile;
    }

    public static void validate(boolean expression, String message) throws ValidationException {
        if (!expression) {
            throw new ValidationException(message);
        }
    }

    private Utils() {
    }
}

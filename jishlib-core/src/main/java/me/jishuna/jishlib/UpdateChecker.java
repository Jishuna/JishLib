package me.jishuna.jishlib;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Consumer;

// From: https://www.spigotmc.org/wiki/creating-an-update-checker-that-checks-for-updates

/**
 * A simple update checker for a plugin. <br>
 * See <a
 * href=https://www.spigotmc.org/wiki/creating-an-update-checker-that-checks-for-updates>https://www.spigotmc.org/wiki/creating-an-update-checker-that-checks-for-updates</a>
 */
public class UpdateChecker {

    private final JavaPlugin plugin;
    private final int resourceId;

    /**
     * Creates an update checker for the given plugin.
     *
     * @param plugin     the plugin to check for
     * @param resourceId the spigotmc resource id for the provided plugin
     */
    public UpdateChecker(JavaPlugin plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    /**
     * Applies the given consumer to the current published version of this plugin.
     *
     * @param consumer the consumer to apply
     */
    public void checkVersion(final Consumer<String> consumer) {
        try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream();
                Scanner scanner = new Scanner(inputStream)) {
            if (scanner.hasNext()) {
                consumer.accept(scanner.next());
            }
        } catch (IOException exception) {
            this.plugin.getLogger().warning("Unable to check for updates: " + exception.getMessage());
        }
    }
}

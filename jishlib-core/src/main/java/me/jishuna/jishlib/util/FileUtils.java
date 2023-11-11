package me.jishuna.jishlib.util;

import com.google.common.base.Preconditions;
import java.io.File;
import java.io.FilenameFilter;
import java.util.function.Consumer;
import org.bukkit.plugin.Plugin;

/**
 * Utility methods for {@link File}.
 */
public class FileUtils {
    /**
     * A file filter that only matches yml files (.yml or .yaml)
     */
    public static final FilenameFilter YML_FILE_FILTER = (dir, name) -> name.endsWith(".yml") || name.endsWith(".yaml");

    /**
     * Saves a plugin resource without triggering a message when the resource
     * already exists.
     *
     * @param plugin       the plugin to get the resource from
     * @param resourcePath the path to the resource
     * @see Plugin#saveResource(String, boolean)
     */
    public static void saveResource(Plugin plugin, String resourcePath) {
        saveResource(plugin, resourcePath, false);
    }

    /**
     * Saves a plugin resource without triggering a message when the resource
     * already exists.
     *
     * @param plugin       the plugin to get the resource from
     * @param resourcePath the path to the resource
     * @param replace      whether to overwrite the contents of the existing file.
     * @see Plugin#saveResource(String, boolean)
     */
    public static void saveResource(Plugin plugin, String resourcePath, boolean replace) {
        Preconditions.checkArgument(plugin.getResource(resourcePath) != null, "The embedded resource '" + resourcePath + "' cannot be found in " + plugin.getName());
        File resourceFile = new File(plugin.getDataFolder(), resourcePath);

        if (replace || !resourceFile.exists()) {
            plugin.saveResource(resourcePath, replace);
        }
    }

    /**
     * Applies the given consumer to all files in the target folder.
     *
     * @param folder   the folder to use
     * @param consumer the consumer to apply to each file
     * @param deep     if true, will also apply the consumer to all files in any
     *                 subfolders
     */
    public static void visitFiles(File folder, Consumer<File> consumer, boolean deep) {
        visitFiles(folder, null, consumer, deep);
    }

    /**
     * Applies the given consumer to all files in the target folder that match the
     * given filter.
     *
     * @param folder   the folder to use
     * @param filter   the filter to use
     * @param consumer the consumer to apply to each file
     * @param deep     if true, will also apply the consumer to all files in any
     *                 subfolders
     * @see FileUtils#YML_FILE_FILTER
     */
    public static void visitFiles(File folder, FilenameFilter filter, Consumer<File> consumer, boolean deep) {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                if (deep) {
                    visitFiles(file, filter, consumer, deep);
                }
                continue;
            }

            if (filter.accept(folder, file.getName())) {
                consumer.accept(file);
            }
        }
    }

    private FileUtils() {
    }
}

package me.jishuna.jishlib.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Optional;
import java.util.function.Consumer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class FileUtils {
    public static final FilenameFilter YML_FILE_FILTER = (dir, name) -> name.endsWith(".yml") || name.endsWith(".yaml");

    private FileUtils() {
    }

    public static Optional<YamlConfiguration> loadResource(Plugin source, String resourceName) {
        Optional<File> optional = loadResourceFile(source, resourceName);

        if (optional.isPresent()) {
            return Optional.of(YamlConfiguration.loadConfiguration(optional.get()));
        }
        return Optional.empty();
    }

    public static Optional<File> loadResourceFile(Plugin source, String resourceName) {
        File resourceFile = new File(source.getDataFolder(), resourceName);

        // Copy file if needed
        if (!resourceFile.exists() && source.getResource(resourceName) != null) {
            source.saveResource(resourceName, false);
        }

        // File still doesn't exist, return empty
        if (!resourceFile.exists()) {
            return Optional.empty();
        }
        return Optional.of(resourceFile);
    }

    public static void saveResource(Plugin source, String resourceName) {
        File resourceFile = new File(source.getDataFolder(), resourceName);

        // Copy file if needed
        if (!resourceFile.exists() && source.getResource(resourceName) != null) {
            source.saveResource(resourceName, false);
        }
    }

    public static void visitFiles(File folder, Consumer<File> consumer) {
        visitFiles(folder, null, consumer);
    }

    public static void visitFiles(File folder, FilenameFilter filter, Consumer<File> consumer) {
        for (File file : folder.listFiles(filter)) {
            if (file.isDirectory()) {
                visitFiles(file, consumer);
            }

            consumer.accept(file);
        }
    }
}

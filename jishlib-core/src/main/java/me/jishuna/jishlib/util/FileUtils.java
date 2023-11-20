package me.jishuna.jishlib.util;

import com.google.common.base.Preconditions;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import org.bukkit.plugin.Plugin;

/**
 * Utility methods for {@link File}.
 */
public class FileUtils {
    public static final long ONE_KB = 1024;
    public static final long ONE_MB = ONE_KB * ONE_KB;
    public static final long ONE_GB = ONE_KB * ONE_MB;

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

    /**
     * Gets the human readable size of a file/directory.
     *
     * @param file the file/directory
     * @return human readable size
     * @see #getReadableSize
     */
    public static String getReadableFileSize(File file) {
        return getReadableSize(getSize(file.toPath()));
    }

    /**
     * Gets the human readable size of a path.
     *
     * @param path the path
     * @return human readable size
     * @see #getReadableSize
     */
    public static String getReadableFileSize(Path path) {
        return getReadableSize(getSize(path));
    }

    /**
     * Gets the human readable size of a file. The return value is affixed with the
     * largest applicable unit (KB, MB, GB) and rounded to 2 decimal places.
     *
     * @param size the size of the file in bytes
     * @return human readable size
     */
    public static String getReadableSize(long size) {
        final String displaySize;

        if (size > ONE_GB) {
            displaySize = String.format("%.2fGB", size / (double) ONE_GB);
        } else if (size > ONE_MB) {
            displaySize = String.format("%.2fMB", size / (double) ONE_MB);
        } else if (size > ONE_KB) {
            displaySize = String.format("%.2fKB", size / (double) ONE_KB);
        } else {
            displaySize = size + " bytes";
        }
        return displaySize;
    }

    /**
     * Gets the size of a file/directory in bytes
     *
     * @param file the file/directory to get the size of
     * @return the size in bytes
     * @see #getSize(Path)
     */
    public static long getSize(File file) {
        return getSize(file.toPath());
    }

    /**
     * Gets the size of a path in bytes. Skips any Symlinks, inaccessible
     * directories, or directories that cause problems.
     *
     * @param path the path to get the size of
     * @return the size in bytes
     */
    public static long getSize(Path path) {
        final AtomicLong size = new AtomicLong(0);

        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    size.addAndGet(attrs.size());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    // Skip folders that can't be traversed
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    // Ignore errors traversing a folder
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new AssertionError("walkFileTree will not throw IOException if the FileVisitor does not");
        }

        return size.get();
    }

    private FileUtils() {
    }
}

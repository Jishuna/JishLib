package me.jishuna.jishlib.util;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    public static boolean createWithParents(File file) {
        if (file.exists()) {
            return true;
        }

        if (file.isDirectory()) {
            return file.mkdirs();
        }

        boolean success = true;
        File parent = file.getParentFile();
        if (parent != null) {
            success &= parent.mkdirs();
        }

        try {
            return success & file.createNewFile();
        } catch (IOException e) {
            return false;
        }
    }

    private FileUtils() {
    }
}

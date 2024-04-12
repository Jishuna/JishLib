package me.jishuna.jishlib.message;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.bukkit.configuration.file.YamlConfiguration;
import me.jishuna.jishlib.JishLib;

public class MessageLoader {
    private final String fileName;

    public MessageLoader(String fileName) {
        this.fileName = fileName;
    }

    public Map<String, Object> load() {
        YamlConfiguration saved = readSaved();
        YamlConfiguration internal = readInternal();

        merge(internal, saved);
        save(saved);

        return saved.getValues(true);
    }

    private void merge(YamlConfiguration from, YamlConfiguration to) {
        from.getValues(true).forEach((k, v) -> {
            if (!to.isSet(k)) {
                to.set(k, v);

                to.setComments(k, from.getComments(k));
                to.setInlineComments(k, from.getInlineComments(k));
            }
        });
    }

    private YamlConfiguration readSaved() {
        File file = new File(JishLib.getPlugin().getDataFolder(), this.fileName);

        if (file.exists()) {
            return YamlConfiguration.loadConfiguration(file);
        }

        return new YamlConfiguration();
    }

    private YamlConfiguration readInternal() {
        try (InputStream stream = JishLib.getPlugin().getResource(this.fileName);
                Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            return YamlConfiguration.loadConfiguration(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new YamlConfiguration();
    }

    private void save(YamlConfiguration config) {
        File file = new File(JishLib.getPlugin().getDataFolder(), this.fileName);

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

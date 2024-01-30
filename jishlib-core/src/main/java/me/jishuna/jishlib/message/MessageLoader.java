package me.jishuna.jishlib.message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.configuration.file.YamlConstructor;
import org.bukkit.configuration.file.YamlRepresenter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import me.jishuna.jishlib.JishLib;

public class MessageLoader {
    private static final Yaml YAML = createYaml();
    private final String fileName;

    public MessageLoader(String fileName) {
        this.fileName = fileName;
    }

    public Map<String, Object> load() {
        Map<String, Object> saved = new LinkedHashMap<>(readSaved());
        Map<String, Object> internal = new LinkedHashMap<>(readInternal());

        boolean needsSaving = merge(internal, saved);
        if (needsSaving) {
            save(saved);
        }

        return saved;
    }

    private boolean merge(Map<String, Object> from, Map<String, Object> to) {
        boolean modified = false;

        for (Entry<String, Object> entry : from.entrySet()) {
            if (to.putIfAbsent(entry.getKey(), entry.getValue()) == null) {
                modified = true;
            }
        }

        return modified;
    }

    private Map<String, Object> readSaved() {
        File file = new File(JishLib.getPlugin().getDataFolder(), this.fileName);

        if (file.exists()) {
            try (InputStream stream = new FileInputStream(file)) {
                return YAML.load(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Collections.emptyMap();
    }

    private Map<String, Object> readInternal() {
        try (InputStream stream = JishLib.getPlugin().getResource(this.fileName)) {
            return YAML.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyMap();
    }

    private void save(Map<String, Object> map) {
        File file = new File(JishLib.getPlugin().getDataFolder(), this.fileName);

        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            YAML.dump(map, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Yaml createYaml() {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        dumperOptions.setSplitLines(false);
        dumperOptions.setProcessComments(true);

        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setProcessComments(true);
        loaderOptions.setMaxAliasesForCollections(Integer.MAX_VALUE); // SPIGOT-5881: Not ideal, but was default pre SnakeYAML 1.26
        loaderOptions.setCodePointLimit(Integer.MAX_VALUE); // SPIGOT-7161: Not ideal, but was default pre SnakeYAML 1.32

        YamlConstructor constructor = new YamlConstructor(loaderOptions);
        YamlRepresenter representer = new YamlRepresenter(dumperOptions);

        representer.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        return new Yaml(constructor, representer, dumperOptions, loaderOptions);
    }
}

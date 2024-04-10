package me.jishuna.jishlib.message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.configuration.file.YamlConstructor;
import org.bukkit.configuration.file.YamlRepresenter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import me.jishuna.jishlib.JishLib;

public class MessageLoader {
    public static YamlConstructor CONSTRUCTOR;
    public static final Yaml YAML;

    static {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        dumperOptions.setSplitLines(false);
        dumperOptions.setProcessComments(true);

        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setProcessComments(true);
        loaderOptions.setMaxAliasesForCollections(Integer.MAX_VALUE); // SPIGOT-5881: Not ideal, but was default pre SnakeYAML 1.26
        loaderOptions.setCodePointLimit(Integer.MAX_VALUE); // SPIGOT-7161: Not ideal, but was default pre SnakeYAML 1.32

        CONSTRUCTOR = new YamlConstructor(loaderOptions);
        YamlRepresenter representer = new YamlRepresenter(dumperOptions);

        representer.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        YAML = new Yaml(CONSTRUCTOR, representer, dumperOptions, loaderOptions);
    }

    private final String fileName;

    public MessageLoader(String fileName) {
        this.fileName = fileName;
    }

    public MappingNode load() {
        MappingNode node = merge(readSaved(), readInternal());
        save(node);

        return node;
    }

    private MappingNode merge(MappingNode saved, MappingNode internal) {
        if (saved == null) {
            return internal;
        }

        Set<String> paths = new HashSet<>();
        saved.getValue().forEach(tuple -> paths.add(String.valueOf(CONSTRUCTOR.construct(tuple.getKeyNode()))));

        List<NodeTuple> nodes = new ArrayList<>(saved.getValue());

        for (NodeTuple tuple : internal.getValue()) {
            String path = String.valueOf(CONSTRUCTOR.construct(tuple.getKeyNode()));
            if (!paths.contains(path)) {
                nodes.add(tuple);
            }
        }

        saved.setValue(nodes);
        return saved;
    }

    private MappingNode readSaved() {
        File file = new File(JishLib.getPlugin().getDataFolder(), this.fileName);

        if (file.exists()) {
            try (InputStream stream = new FileInputStream(file);
                    Reader reader = new InputStreamReader(stream)) {
                Node node = YAML.compose(reader);
                if (node instanceof MappingNode mapping) {
                    CONSTRUCTOR.flattenMapping(mapping);
                    return mapping;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private MappingNode readInternal() {
        try (InputStream stream = JishLib.getPlugin().getResource(this.fileName);
                Reader reader = new InputStreamReader(stream)) {
            Node node = YAML.compose(reader);
            if (node instanceof MappingNode mapping) {
                CONSTRUCTOR.flattenMapping(mapping);
                return mapping;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void save(Node node) {
        File file = new File(JishLib.getPlugin().getDataFolder(), this.fileName);

        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            YAML.serialize(node, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package me.jishuna.jishlib.data.source.yaml;

import java.io.File;
import java.io.IOException;
import java.util.Stack;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import me.jishuna.jishlib.data.object.ArrayDataObject;
import me.jishuna.jishlib.data.object.BooleanDataObject;
import me.jishuna.jishlib.data.object.DataObject;
import me.jishuna.jishlib.data.object.ListDataObject;
import me.jishuna.jishlib.data.object.MapDataObject;
import me.jishuna.jishlib.data.object.NumericDataObject;
import me.jishuna.jishlib.data.object.StringDataObject;
import me.jishuna.jishlib.data.source.DataWriter;

public class YamlWriter implements DataWriter {
    private final File file;
    private final YamlConfiguration root;
    private final Stack<Object> stack = new Stack<>();

    private YamlWriter(File file, YamlConfiguration config) {
        this.file = file;
        this.root = config;
        this.stack.add(config);
    }

    public static YamlWriter create(File file) {
        return new YamlWriter(file, new YamlConfiguration());
    }

    public static YamlWriter create(File file, YamlConfiguration config) {
        return new YamlWriter(file, config);
    }

    @Override
    public void writeMap(String name, MapDataObject value) throws IOException {
        YamlConfiguration config = new YamlConfiguration();
        this.stack.push(config);

        for (DataObject<?> v : value.get().values()) {
            v.write(this);
        }

        this.stack.pop();

        Object object = this.stack.peek();
        if (object instanceof ConfigurationList list) {
            list.add(config);
        } else if (object instanceof ConfigurationSection section) {
            section.set(name, config);
        }
    }

    @Override
    public void writeList(String name, ListDataObject value) throws IOException {
        ConfigurationList config = new ConfigurationList();
        this.stack.push(config);

        for (DataObject<?> v : value) {
            v.write(this);
        }

        this.stack.pop();

        Object object = this.stack.peek();
        if (object instanceof ConfigurationList list) {
            list.add(config);
        } else if (object instanceof ConfigurationSection section) {
            section.set(name, config);
        }
    }

    @Override
    public void writeArray(String name, ArrayDataObject value) throws IOException {
        writeList(name, value);
    }

    @Override
    public void writeString(String name, StringDataObject value) {
        Object object = this.stack.peek();
        if (object instanceof ConfigurationList list) {
            list.add(value.get());
        } else if (object instanceof ConfigurationSection section) {
            section.set(name, value.get());
        }
    }

    @Override
    public void writeNumber(String name, NumericDataObject<?> value) {
        Object object = this.stack.peek();
        if (object instanceof ConfigurationList list) {
            list.add(value.get());
        } else if (object instanceof ConfigurationSection section) {
            section.set(name, value.get());
        }
    }

    @Override
    public void writeBoolean(String name, BooleanDataObject value) {
        Object object = this.stack.peek();
        if (object instanceof ConfigurationList list) {
            list.add(value.get());
        } else if (object instanceof ConfigurationSection section) {
            section.set(name, value.get());
        }
    }

    @Override
    public void close() throws IOException {
        this.root.save(this.file);
        this.stack.clear();
    }
}

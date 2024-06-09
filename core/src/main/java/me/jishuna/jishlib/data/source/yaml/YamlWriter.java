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
    private final YamlConfiguration root;
    private final Stack<Object> stack = new Stack<>();

    private YamlWriter(YamlConfiguration config) {
        this.root = config;
        this.stack.add(this.root);
    }

    public static YamlWriter create() {
        return new YamlWriter(new YamlConfiguration());
    }

    public static YamlWriter create(YamlConfiguration config) {
        return new YamlWriter(config);
    }

    @Override
    public void save(File file) throws IOException {
        this.root.save(file);
    }

    @Override
    public void writeMap(String name, MapDataObject value) throws IOException {
        boolean pop = false;
        if (!name.isBlank()) {
            YamlConfiguration config = new YamlConfiguration();
            Object object = this.stack.peek();
            if (object instanceof ConfigurationList list) {
                list.add(config);
            } else if (object instanceof ConfigurationSection section) {
                section.set(name, config);
            }

            this.stack.push(config);
            pop = true;
        }

        for (DataObject<?> v : value.get().values()) {
            v.write(this);
        }

        if (pop) {
            this.stack.pop();
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

    public YamlConfiguration getValue() {
        return this.root;
    }
}

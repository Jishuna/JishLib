package me.jishuna.jishlib.data.source.yaml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import me.jishuna.jishlib.data.holder.BooleanDataHolder;
import me.jishuna.jishlib.data.holder.DataHolder;
import me.jishuna.jishlib.data.holder.StringDataHolder;
import me.jishuna.jishlib.data.holder.collection.ArrayDataHolder;
import me.jishuna.jishlib.data.holder.collection.ListDataHolder;
import me.jishuna.jishlib.data.holder.collection.MapDataHolder;
import me.jishuna.jishlib.data.holder.number.ByteDataHolder;
import me.jishuna.jishlib.data.holder.number.DoubleDataHolder;
import me.jishuna.jishlib.data.holder.number.FloatDataHolder;
import me.jishuna.jishlib.data.holder.number.IntDataHolder;
import me.jishuna.jishlib.data.holder.number.LongDataHolder;
import me.jishuna.jishlib.data.holder.number.ShortDataHolder;
import me.jishuna.jishlib.data.source.DataWriter;

public class YamlWriter implements DataWriter<YamlConfiguration> {
    private final YamlConfiguration root;
    private final Deque<Object> stack = new ArrayDeque<>();

    private YamlWriter(YamlConfiguration config) {
        this.root = config;
        this.stack.addFirst(config);
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
    public void writeMap(String name, MapDataHolder value) throws IOException {
        if (this.stack.size() == 1 && name.isBlank()) {
            for (DataHolder<?> v : value) {
                v.write(this);
            }
            return;
        }

        YamlConfiguration subSection = new YamlConfiguration();
        this.stack.addFirst(subSection);

        for (DataHolder<?> v : value) {
            v.write(this);
        }

        this.stack.removeFirst();
        Object object = this.stack.peekFirst();
        if (object instanceof ConfigurationList list) {
            list.add(subSection);
        } else if (object instanceof ConfigurationSection section) {
            section.set(name, subSection);
        }
    }

    @Override
    public void writeList(String name, ListDataHolder value) throws IOException {
        ConfigurationList config = new ConfigurationList();
        this.stack.addFirst(config);

        for (DataHolder<?> v : value) {
            v.write(this);
        }

        this.stack.removeFirst();
        Object object = this.stack.peekFirst();
        if (object instanceof ConfigurationList list) {
            list.add(config);
        } else if (object instanceof ConfigurationSection section) {
            section.set(name, config);
        }
    }

    @Override
    public void writeArray(String name, ArrayDataHolder value) throws IOException {
        writeList(name, value);
    }

    @Override
    public void writeString(String name, StringDataHolder value) {
        write(name, value);
    }

    @Override
    public void writeByte(String name, ByteDataHolder value) throws IOException {
        write(name, value);
    }

    @Override
    public void writeShort(String name, ShortDataHolder value) throws IOException {
        write(name, value);
    }

    @Override
    public void writeInt(String name, IntDataHolder value) throws IOException {
        write(name, value);
    }

    @Override
    public void writeLong(String name, LongDataHolder value) throws IOException {
        write(name, value);
    }

    @Override
    public void writeFloat(String name, FloatDataHolder value) throws IOException {
        write(name, value);
    }

    @Override
    public void writeDouble(String name, DoubleDataHolder value) throws IOException {
        write(name, value);
    }

    @Override
    public void writeBoolean(String name, BooleanDataHolder value) {
        write(name, value);
    }

    @Override
    public void close() throws IOException {
        // Nothing to close
    }

    @Override
    public YamlConfiguration getValue() {
        return this.root;
    }

    private void write(String name, DataHolder<?> value) {
        Object object = this.stack.peekFirst();
        if (object instanceof ConfigurationList list) {
            list.add(value.get());
        } else if (object instanceof ConfigurationSection section) {
            section.set(name, value.get());
        }
    }
}

package me.jishuna.jishlib.data.source.nbt;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;
import me.jishuna.jishlib.data.HolderType;
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

public class NBTWriter implements DataWriter<byte[]> {
    private final ByteArrayOutputStream byteStream;
    private final DataOutputStream writer;
    private boolean hasTag = false;

    private NBTWriter() {
        this.byteStream = new ByteArrayOutputStream();
        this.writer = new DataOutputStream(this.byteStream);
    }

    public static NBTWriter create() {
        return new NBTWriter();
    }

    @Override
    public void save(File file) throws IOException {
        save(new BufferedOutputStream(new FileOutputStream(file)));
    }

    public void save(File file, CompressionType type) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            switch (type) {
            case NONE -> save(new DataOutputStream(bos));
            case GZIP -> save(new DataOutputStream(new GZIPOutputStream(bos)));
            case ZLIB -> save(new DataOutputStream(new DeflaterOutputStream(bos)));
            }
        }
    }

    private void save(OutputStream output) throws IOException {
        try (output) {
            this.byteStream.writeTo(output);
        }
    }

    @Override
    public void writeMap(String name, MapDataHolder value) throws IOException {
        if (!this.hasTag) {
            this.writer.writeByte(HolderType.MAP.id());
            this.writer.writeUTF(value.getName());
            this.hasTag = true;
        }

        for (DataHolder<?> v : value.get().values()) {
            this.writer.writeByte(v.getType().id());
            this.writer.writeUTF(v.getName());

            v.write(this);
        }

        this.writer.writeByte(0);
    }

    @Override
    public void writeList(String name, ListDataHolder value) throws IOException {
        List<DataHolder<?>> list = value.get();
        if (list.isEmpty()) {
            this.writer.writeByte(0);
        } else {
            this.writer.writeByte(list.get(0).getType().id());
        }

        this.writer.writeInt(list.size());
        for (DataHolder<?> object : value) {
            object.write(this);
        }
    }

    @Override
    public void writeArray(String name, ArrayDataHolder value) throws IOException {
        this.writer.writeInt(value.get().size());
        for (DataHolder<?> object : value) {
            object.write(this);
        }
    }

    @Override
    public void writeString(String name, StringDataHolder value) throws IOException {
        this.writer.writeUTF(value.get());
    }

    @Override
    public void writeByte(String name, ByteDataHolder value) throws IOException {
        this.writer.writeByte(value.get());
    }

    @Override
    public void writeShort(String name, ShortDataHolder value) throws IOException {
        this.writer.writeShort(value.get());
    }

    @Override
    public void writeInt(String name, IntDataHolder value) throws IOException {
        this.writer.writeInt(value.get());
    }

    @Override
    public void writeLong(String name, LongDataHolder value) throws IOException {
        this.writer.writeLong(value.get());
    }

    @Override
    public void writeFloat(String name, FloatDataHolder value) throws IOException {
        this.writer.writeFloat(value.get());
    }

    @Override
    public void writeDouble(String name, DoubleDataHolder value) throws IOException {
        this.writer.writeDouble(value.get());
    }

    @Override
    public void writeBoolean(String name, BooleanDataHolder value) throws IOException {
        this.writer.writeByte(value.get() ? 1 : 0);
    }

    @Override
    public void close() throws IOException {
        this.writer.close();
        this.byteStream.close();
    }

    @Override
    public byte[] getValue() {
        return this.byteStream.toByteArray();
    }
}

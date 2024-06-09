package me.jishuna.jishlib.data.source.nbt;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;
import me.jishuna.jishlib.data.object.ArrayDataObject;
import me.jishuna.jishlib.data.object.BooleanDataObject;
import me.jishuna.jishlib.data.object.DataObject;
import me.jishuna.jishlib.data.object.ListDataObject;
import me.jishuna.jishlib.data.object.MapDataObject;
import me.jishuna.jishlib.data.object.NumericDataObject;
import me.jishuna.jishlib.data.object.StringDataObject;
import me.jishuna.jishlib.data.source.DataWriter;

public class NBTWriter implements DataWriter, Closeable {
    private final ByteArrayOutputStream byteStream;
    private final DataOutputStream writer;
    private boolean hasTag = false;

    private NBTWriter() throws IOException {
        this.byteStream = new ByteArrayOutputStream();
        this.writer = new DataOutputStream(this.byteStream);
    }

    public static NBTWriter create() throws IOException {
        return new NBTWriter();
    }

    @Override
    public void save(File file) throws IOException {
        save(new BufferedOutputStream(new FileOutputStream(file)));
    }

    public void save(File file, CompressionType type) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        switch (type) {
        case NONE -> save(new DataOutputStream(bos));
        case GZIP -> save(new DataOutputStream(new GZIPOutputStream(bos)));
        case ZLIB -> save(new DataOutputStream(new DeflaterOutputStream(bos)));
        }
    }

    private void save(OutputStream output) throws IOException {
        try (output) {
            this.byteStream.writeTo(output);
        }
    }

    @Override
    public void writeMap(String name, MapDataObject value) throws IOException {
        if (!this.hasTag) {
            this.writer.writeByte(TagType.COMPOUND.id());
            this.writer.writeUTF(value.getName());
            this.hasTag = true;
        }

        for (DataObject<?> v : value.get().values()) {
            this.writer.writeByte(getTagType(v));
            this.writer.writeUTF(v.getName());

            v.write(this);
        }

        this.writer.writeByte(0);
    }

    @Override
    public void writeList(String name, ListDataObject value) throws IOException {
        List<DataObject<?>> list = value.get();
        if (list.isEmpty()) {
            this.writer.writeByte(0);
        } else {
            this.writer.writeByte(getTagType(list.get(0)));
        }

        this.writer.writeInt(list.size());
        for (DataObject<?> object : value) {
            object.write(this);
        }
    }

    @Override
    public void writeArray(String name, ArrayDataObject value) throws IOException {
        this.writer.writeInt(value.get().size());
        for (DataObject<?> object : value) {
            object.write(this);
        }
    }

    @Override
    public void writeString(String name, StringDataObject value) throws IOException {
        this.writer.writeUTF(value.get());
    }

    @Override
    public void writeNumber(String name, NumericDataObject<?> value) throws IOException {
        Number num = value.get();
        if (num.getClass() == Byte.class) {
            this.writer.writeByte(num.byteValue());
        } else if (num.getClass() == Short.class) {
            this.writer.writeShort(num.shortValue());
        } else if (num.getClass() == Integer.class) {
            this.writer.writeInt(num.intValue());
        } else if (num.getClass() == Long.class) {
            this.writer.writeLong(num.longValue());
        } else if (num.getClass() == Float.class) {
            this.writer.writeFloat(num.floatValue());
        } else if (num.getClass() == Double.class) {
            this.writer.writeDouble(num.doubleValue());
        }
    }

    @Override
    public void writeBoolean(String name, BooleanDataObject value) throws IOException {
        this.writer.writeByte(value.get() ? 1 : 0);
    }

    private byte getTagType(DataObject<?> object) {
        if (object instanceof ArrayDataObject array) {
            List<DataObject<?>> list = array.get();
            if (list.isEmpty()) {
                return TagType.BYTE_ARRAY.id();
            }

            return switch (getTagType(list.get(0))) {
            case 3 -> TagType.INT_ARRAY.id();
            case 4 -> TagType.LONG_ARRAY.id();
            default -> TagType.BYTE_ARRAY.id();
            };
        }

        if (object instanceof MapDataObject) {
            return TagType.COMPOUND.id();
        }

        if (object instanceof ListDataObject) {
            return TagType.LIST.id();
        }

        if (object instanceof StringDataObject) {
            return TagType.STRING.id();
        }

        if (object instanceof BooleanDataObject) {
            return TagType.BYTE.id();
        }

        if (object instanceof NumericDataObject<?> numeric) {
            Number num = numeric.get();
            if (num.getClass() == Byte.class) {
                return TagType.BYTE.id();
            }

            if (num.getClass() == Short.class) {
                return TagType.SHORT.id();
            }

            if (num.getClass() == Integer.class) {
                return TagType.INT.id();
            }

            if (num.getClass() == Long.class) {
                return TagType.LONG.id();
            }

            if (num.getClass() == Float.class) {
                return TagType.FLOAT.id();
            }

            if (num.getClass() == Double.class) {
                return TagType.DOUBLE.id();
            }
        }

        return 0;
    }

    @Override
    public void close() throws IOException {
        this.writer.close();
        this.byteStream.close();
    }

    public byte[] getValue() {
        return this.byteStream.toByteArray();
    }
}

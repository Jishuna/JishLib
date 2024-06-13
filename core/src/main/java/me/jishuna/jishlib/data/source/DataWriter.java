package me.jishuna.jishlib.data.source;

import java.io.File;
import java.io.IOException;
import me.jishuna.jishlib.data.holder.BooleanDataHolder;
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

public interface DataWriter {
    public void writeMap(String name, MapDataHolder value) throws IOException;

    public void writeList(String name, ListDataHolder value) throws IOException;

    public void writeArray(String name, ArrayDataHolder value) throws IOException;

    public void writeString(String name, StringDataHolder value) throws IOException;

    public void writeByte(String name, ByteDataHolder value) throws IOException;

    public void writeShort(String name, ShortDataHolder value) throws IOException;

    public void writeInt(String name, IntDataHolder value) throws IOException;

    public void writeLong(String name, LongDataHolder value) throws IOException;

    public void writeFloat(String name, FloatDataHolder value) throws IOException;

    public void writeDouble(String name, DoubleDataHolder value) throws IOException;

    public void writeBoolean(String name, BooleanDataHolder value) throws IOException;

    public void save(File file) throws IOException;
}

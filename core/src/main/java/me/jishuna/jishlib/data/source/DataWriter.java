package me.jishuna.jishlib.data.source;

import java.io.File;
import java.io.IOException;
import me.jishuna.jishlib.data.object.ArrayDataObject;
import me.jishuna.jishlib.data.object.BooleanDataObject;
import me.jishuna.jishlib.data.object.ListDataObject;
import me.jishuna.jishlib.data.object.MapDataObject;
import me.jishuna.jishlib.data.object.NumericDataObject;
import me.jishuna.jishlib.data.object.StringDataObject;

public interface DataWriter {
    public void writeMap(String name, MapDataObject value) throws IOException;

    public void writeList(String name, ListDataObject value) throws IOException;

    public void writeArray(String name, ArrayDataObject value) throws IOException;

    public void writeString(String name, StringDataObject value) throws IOException;

    public void writeNumber(String name, NumericDataObject<?> value) throws IOException;

    public void writeBoolean(String name, BooleanDataObject value) throws IOException;

    public void save(File file) throws IOException;
}

package me.jishuna.jishlib.data.object;

import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.jishuna.jishlib.data.source.nbt.TagType;

public class ArrayDataObject extends ListDataObject {

    private ArrayDataObject(String name, List<DataObject<?>> value) {
        super(name, value);
    }

    public static ArrayDataObject empty(String name) {
        return new ArrayDataObject(name, new ArrayList<>());
    }

    public static ArrayDataObject of(List<DataObject<?>> value) {
        return of("", value);
    }

    public static ArrayDataObject of(String name, List<DataObject<?>> value) {
        return new ArrayDataObject(name, value);
    }

    @Override
    public void add(DataObject<?> value) {
        this.value.add(value);
    }

    public void merge(ArrayDataObject other) {
        other.forEach(this::add);
    }

    @Override
    public Object serialize() {
        List<Object> list = new ArrayList<>();
        this.value.forEach(o -> list.add(o.serialize()));

        return list;
    }

    @Override
    public Iterator<DataObject<?>> iterator() {
        return this.value.iterator();
    }

    @Override
    public byte getTagType() {
        if (this.value.isEmpty()) {
            return TagType.BYTE_ARRAY.id();
        }

        byte entryType = this.value.get(0).getTagType();
        if (entryType == TagType.INT.id()) {
            return TagType.INT_ARRAY.id();
        }

        if (entryType == TagType.LONG.id()) {
            return TagType.LONG_ARRAY.id();
        }

        return TagType.BYTE_ARRAY.id();
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeInt(this.value.size());

        for (DataObject<?> object : this) {
            object.write(output);
        }
    }
}

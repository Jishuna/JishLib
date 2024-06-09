package me.jishuna.jishlib.data.object;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.jishuna.jishlib.data.source.DataWriter;

public class ListDataObject extends DataObject<List<DataObject<?>>> implements Iterable<DataObject<?>> {

    protected ListDataObject(String name, List<DataObject<?>> value) {
        super(name, value);
    }

    public static ListDataObject empty(String name) {
        return new ListDataObject(name, new ArrayList<>());
    }

    public static ListDataObject of(List<DataObject<?>> value) {
        return of("", value);
    }

    public static ListDataObject of(String name, List<DataObject<?>> value) {
        return new ListDataObject(name, value);
    }

    public void add(DataObject<?> value) {
        this.value.add(value);
    }

    public void merge(ListDataObject other) {
        other.forEach(this::add);
    }

    @Override
    public Iterator<DataObject<?>> iterator() {
        return this.value.iterator();
    }

    @Override
    public void write(DataWriter writer) throws IOException {
        writer.writeList(this.name, this);
    }
}

package me.jishuna.jishlib.data.holder.collection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.jishuna.jishlib.data.HolderType;
import me.jishuna.jishlib.data.holder.DataHolder;
import me.jishuna.jishlib.data.source.DataWriter;

public class ListDataHolder extends DataHolder<List<DataHolder<?>>> implements Iterable<DataHolder<?>> {

    protected ListDataHolder(String name, List<DataHolder<?>> value) {
        super(name, value);
    }

    public static ListDataHolder empty(String name) {
        return new ListDataHolder(name, new ArrayList<>());
    }

    public static ListDataHolder of(List<DataHolder<?>> value) {
        return of("", value);
    }

    public static ListDataHolder of(String name, List<DataHolder<?>> value) {
        return new ListDataHolder(name, value);
    }

    public void add(DataHolder<?> value) {
        this.value.add(value);
    }

    public void merge(ListDataHolder other) {
        other.forEach(this::add);
    }

    @Override
    public Iterator<DataHolder<?>> iterator() {
        return this.value.iterator();
    }

    @Override
    public void write(DataWriter<?> writer) throws IOException {
        writer.writeList(this.name, this);
    }

    @Override
    public HolderType getType() {
        return HolderType.LIST;
    }
}

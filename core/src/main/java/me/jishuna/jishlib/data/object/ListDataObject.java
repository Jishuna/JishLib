package me.jishuna.jishlib.data.object;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListDataObject extends DataObject<List<DataObject<?>>> implements Iterable<DataObject<?>> {

    private ListDataObject(List<DataObject<?>> value) {
        super(value);
    }

    public static ListDataObject of(List<DataObject<?>> value) {
        return new ListDataObject(value);
    }

    public void add(DataObject<?> value) {
        this.value.add(value);
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
}

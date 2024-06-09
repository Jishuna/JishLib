package me.jishuna.jishlib.data.object;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import me.jishuna.jishlib.data.source.DataWriter;

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
    public void write(DataWriter writer) throws IOException {
        writer.writeArray(this.name, this);
    }
}

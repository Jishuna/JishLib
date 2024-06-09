package me.jishuna.jishlib.data.object;

import java.io.IOException;
import me.jishuna.jishlib.data.source.DataWriter;

public class StringDataObject extends DataObject<String> {
    protected StringDataObject(String name, String value) {
        super(name, value);
    }

    public static StringDataObject of(String value) {
        return of("", value);
    }

    public static StringDataObject of(String name, String value) {
        return new StringDataObject(name, value);
    }

    @Override
    public void write(DataWriter writer) throws IOException {
        writer.writeString(this.name, this);
    }
}

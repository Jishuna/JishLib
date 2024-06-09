package me.jishuna.jishlib.data.object;

import java.io.IOException;
import me.jishuna.jishlib.data.source.DataWriter;

public class BooleanDataObject extends DataObject<Boolean> {

    protected BooleanDataObject(String name, Boolean value) {
        super(name, value);
    }

    public static BooleanDataObject of(Boolean value) {
        return of("", value);
    }

    public static BooleanDataObject of(String name, Boolean value) {
        return new BooleanDataObject(name, value);
    }

    @Override
    public void write(DataWriter writer) throws IOException {
        writer.writeBoolean(this.name, this);
    }

}

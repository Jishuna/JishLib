package me.jishuna.jishlib.data.object;

import java.io.DataOutput;
import java.io.IOException;
import me.jishuna.jishlib.data.source.nbt.TagType;

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
    public byte getTagType() {
        return TagType.STRING.id();
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeUTF(this.value);
    }
}

package me.jishuna.jishlib.data.object;

import java.io.DataOutput;
import java.io.IOException;
import me.jishuna.jishlib.data.source.nbt.TagType;

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
    public byte getTagType() {
        return TagType.BYTE.id();
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeByte(this.value ? 1 : 0);
    }

}

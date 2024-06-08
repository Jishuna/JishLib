package me.jishuna.jishlib.data.object;

import java.io.DataOutput;
import java.io.IOException;
import me.jishuna.jishlib.data.source.nbt.TagType;

public class NumericDataObject<T extends Number> extends DataObject<T> {
    protected NumericDataObject(String name, T value) {
        super(name, value);
    }

    public static <T extends Number> NumericDataObject<T> of(T number) {
        return of("", number);
    }

    public static <T extends Number> NumericDataObject<T> of(String name, T number) {
        return new NumericDataObject<>(name, number);
    }

    public byte byteValue() {
        return this.value.byteValue();
    }

    public short shortValue() {
        return this.value.shortValue();
    }

    public int intValue() {
        return this.value.intValue();
    }

    public long longValue() {
        return this.value.longValue();
    }

    public float floatValue() {
        return this.value.floatValue();
    }

    public double doubleValue() {
        return this.value.doubleValue();
    }

    @Override
    public byte getTagType() {
        if (this.value.getClass() == Byte.class) {
            return TagType.BYTE.id();
        }

        if (this.value.getClass() == Short.class) {
            return TagType.SHORT.id();
        }

        if (this.value.getClass() == Integer.class) {
            return TagType.INT.id();
        }

        if (this.value.getClass() == Long.class) {
            return TagType.LONG.id();
        }

        if (this.value.getClass() == Float.class) {
            return TagType.FLOAT.id();
        }

        return TagType.DOUBLE.id();
    }

    @Override
    public void write(DataOutput output) throws IOException {
        if (this.value.getClass() == Byte.class) {
            output.writeByte(this.value.byteValue());
            return;
        }

        if (this.value.getClass() == Short.class) {
            output.writeShort(this.value.shortValue());
            return;
        }

        if (this.value.getClass() == Integer.class) {
            output.writeInt(this.value.intValue());
            return;
        }

        if (this.value.getClass() == Long.class) {
            output.writeLong(this.value.longValue());
            return;
        }

        if (this.value.getClass() == Float.class) {
            output.writeFloat(this.value.floatValue());
            return;
        }

        output.writeDouble(this.value.doubleValue());
    }
}

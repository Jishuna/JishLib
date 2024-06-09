package me.jishuna.jishlib.data.object;

import java.io.IOException;
import me.jishuna.jishlib.data.source.DataWriter;

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
    public void write(DataWriter writer) throws IOException {
        writer.writeNumber(this.name, this);
    }
}

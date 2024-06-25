package me.jishuna.jishlib.data.holder.number;

import java.io.IOException;
import me.jishuna.jishlib.data.HolderType;
import me.jishuna.jishlib.data.source.DataWriter;

public class ShortDataHolder extends NumericDataHolder<Short> {

    protected ShortDataHolder(String name, Short value) {
        super(name, value);
    }

    public static ShortDataHolder of(Short value) {
        return of("", value);
    }

    public static ShortDataHolder of(String name, Short value) {
        return new ShortDataHolder(name, value);
    }

    @Override
    public void write(DataWriter<?> writer) throws IOException {
        writer.writeShort(this.name, this);
    }

    @Override
    public HolderType getType() {
        return HolderType.SHORT;
    }

    @Override
    public byte byteValue() {
        return this.value.byteValue();
    }

    @Override
    public short shortValue() {
        return this.value.shortValue();
    }

    @Override
    public int intValue() {
        return this.value.intValue();
    }

    @Override
    public long longValue() {
        return this.value.longValue();
    }

    @Override
    public float floatValue() {
        return this.value.floatValue();
    }

    @Override
    public double doubleValue() {
        return this.value.doubleValue();
    }

    @Override
    public boolean asBoolean() {
        return this.value.longValue() == 0;
    }

}

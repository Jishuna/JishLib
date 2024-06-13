package me.jishuna.jishlib.data.holder.number;

import java.io.IOException;
import me.jishuna.jishlib.data.HolderType;
import me.jishuna.jishlib.data.source.DataWriter;

public class IntDataHolder extends NumericDataHolder<Integer> {

    protected IntDataHolder(String name, Integer value) {
        super(name, value);
    }

    public static IntDataHolder of(Integer value) {
        return of("", value);
    }

    public static IntDataHolder of(String name, Integer value) {
        return new IntDataHolder(name, value);
    }

    @Override
    public void write(DataWriter writer) throws IOException {
        writer.writeInt(this.name, this);
    }

    @Override
    public HolderType getType() {
        return HolderType.INT;
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

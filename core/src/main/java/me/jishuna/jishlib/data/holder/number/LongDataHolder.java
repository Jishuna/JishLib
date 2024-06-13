package me.jishuna.jishlib.data.holder.number;

import java.io.IOException;
import me.jishuna.jishlib.data.HolderType;
import me.jishuna.jishlib.data.source.DataWriter;

public class LongDataHolder extends NumericDataHolder<Long> {

    protected LongDataHolder(String name, Long value) {
        super(name, value);
    }

    public static LongDataHolder of(Long value) {
        return of("", value);
    }

    public static LongDataHolder of(String name, Long value) {
        return new LongDataHolder(name, value);
    }

    @Override
    public void write(DataWriter writer) throws IOException {
        writer.writeLong(this.name, this);
    }

    @Override
    public HolderType getType() {
        return HolderType.LONG;
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

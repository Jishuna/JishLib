package me.jishuna.jishlib.nms.nbt.tag;

import me.jishuna.jishlib.nms.nbt.NumericTag;
import me.jishuna.jishlib.nms.nbt.TagType;

public class LongTag implements NumericTag<Long> {
    private final long value;

    public LongTag(long value) {
        this.value = value;
    }

    @Override
    public Long getValue() {
        return this.value;
    }

    @Override
    public TagType getType() {
        return TagType.LONG;
    }

    @Override
    public String toString() {
        return "Long (" + this.value + ")";
    }

    @Override
    public byte getAsByte() {
        return (byte) this.value;
    }

    @Override
    public short getAsShort() {
        return (short) this.value;
    }

    @Override
    public int getAsInt() {
        return (int) this.value;
    }

    @Override
    public long getAsLong() {
        return this.value;
    }

    @Override
    public float getAsFloat() {
        return this.value;
    }

    @Override
    public double getAsDouble() {
        return this.value;
    }
}

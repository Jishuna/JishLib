package me.jishuna.jishlib.nms.nbt.tag;

import me.jishuna.jishlib.nms.nbt.NumericTag;
import me.jishuna.jishlib.nms.nbt.TagType;

public class IntTag implements NumericTag<Integer> {
    private final int value;

    public IntTag(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public TagType getType() {
        return TagType.INT;
    }

    @Override
    public String toString() {
        return "Int (" + this.value + ")";
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
        return this.value;
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

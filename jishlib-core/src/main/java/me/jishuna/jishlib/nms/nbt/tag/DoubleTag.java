package me.jishuna.jishlib.nms.nbt.tag;

import me.jishuna.jishlib.nms.nbt.NumericTag;
import me.jishuna.jishlib.nms.nbt.TagType;

public class DoubleTag implements NumericTag<Double> {
    private final double value;

    public DoubleTag(double value) {
        this.value = value;
    }

    @Override
    public Double getValue() {
        return this.value;
    }

    @Override
    public TagType getType() {
        return TagType.DOUBLE;
    }

    @Override
    public String toString() {
        return "Double (" + this.value + ")";
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
        return (long) this.value;
    }

    @Override
    public float getAsFloat() {
        return (float) this.value;
    }

    @Override
    public double getAsDouble() {
        return this.value;
    }
}

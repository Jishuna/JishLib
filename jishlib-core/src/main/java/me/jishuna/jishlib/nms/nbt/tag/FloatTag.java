package me.jishuna.jishlib.nms.nbt.tag;

import me.jishuna.jishlib.nms.nbt.NumericTag;
import me.jishuna.jishlib.nms.nbt.TagType;

public class FloatTag implements NumericTag<Float> {
    private final float value;

    public FloatTag(float value) {
        this.value = value;
    }

    @Override
    public Float getValue() {
        return this.value;
    }

    @Override
    public TagType getType() {
        return TagType.FLOAT;
    }

    @Override
    public String toString() {
        return "Float (" + this.value + ")";
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
        return this.value;
    }

    @Override
    public double getAsDouble() {
        return this.value;
    }
}

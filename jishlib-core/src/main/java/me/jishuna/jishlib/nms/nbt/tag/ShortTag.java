package me.jishuna.jishlib.nms.nbt.tag;

import me.jishuna.jishlib.nms.nbt.NumericTag;
import me.jishuna.jishlib.nms.nbt.TagType;

public class ShortTag implements NumericTag<Short> {
    private final short value;

    public ShortTag(short value) {
        this.value = value;
    }

    @Override
    public Short getValue() {
        return this.value;
    }

    @Override
    public TagType getType() {
        return TagType.SHORT;
    }

    @Override
    public String toString() {
        return "Short (" + this.value + ")";
    }

    @Override
    public byte getAsByte() {
        return (byte) this.value;
    }

    @Override
    public short getAsShort() {
        return this.value;
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

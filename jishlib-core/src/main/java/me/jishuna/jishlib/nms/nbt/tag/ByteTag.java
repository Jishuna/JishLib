package me.jishuna.jishlib.nms.nbt.tag;

import me.jishuna.jishlib.nms.nbt.NumericTag;
import me.jishuna.jishlib.nms.nbt.TagType;

public class ByteTag implements NumericTag<Byte> {
    private final byte value;

    public ByteTag(byte value) {
        this.value = value;
    }

    @Override
    public Byte getValue() {
        return this.value;
    }

    @Override
    public TagType getType() {
        return TagType.BYTE;
    }

    @Override
    public String toString() {
        return "Byte (" + this.value + ")";
    }

    @Override
    public byte getAsByte() {
        return this.value;
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

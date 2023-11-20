package me.jishuna.jishlib.nms.nbt.tag;

import me.jishuna.jishlib.nms.nbt.NBTTag;
import me.jishuna.jishlib.nms.nbt.TagType;

public class ByteArrayTag implements NBTTag<byte[]> {
    private final byte[] value;

    public ByteArrayTag(byte[] value) {
        this.value = value;
    }

    @Override
    public byte[] getValue() {
        return this.value;
    }

    @Override
    public TagType getType() {
        return TagType.BYTE_ARRAY;
    }

    @Override
    public String toString() {
        return "Byte[] (" + this.value + ")";
    }

}

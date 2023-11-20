package me.jishuna.jishlib.nms.nbt.tag;

import me.jishuna.jishlib.nms.nbt.NBTTag;
import me.jishuna.jishlib.nms.nbt.TagType;

public class LongArrayTag implements NBTTag<long[]> {
    private final long[] value;

    public LongArrayTag(long[] value) {
        this.value = value;
    }

    @Override
    public long[] getValue() {
        return this.value;
    }

    @Override
    public TagType getType() {
        return TagType.LONG_ARRAY;
    }

    @Override
    public String toString() {
        return "Long[] (" + this.value + ")";
    }

}

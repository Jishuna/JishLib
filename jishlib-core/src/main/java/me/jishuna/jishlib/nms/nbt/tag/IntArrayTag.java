package me.jishuna.jishlib.nms.nbt.tag;

import me.jishuna.jishlib.nms.nbt.NBTTag;
import me.jishuna.jishlib.nms.nbt.TagType;

public class IntArrayTag implements NBTTag<int[]> {
    private final int[] value;

    public IntArrayTag(int[] value) {
        this.value = value;
    }

    @Override
    public int[] getValue() {
        return this.value;
    }

    @Override
    public TagType getType() {
        return TagType.INT_ARRAY;
    }

    @Override
    public String toString() {
        return "Int[] (" + this.value + ")";
    }

}

package me.jishuna.jishlib.nms.nbt.tag;

import me.jishuna.jishlib.nms.nbt.NBTTag;
import me.jishuna.jishlib.nms.nbt.TagType;

public class StringTag implements NBTTag<String> {
    private final String value;

    public StringTag(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public TagType getType() {
        return TagType.STRING;
    }

    @Override
    public String toString() {
        return "String (" + this.value + ")";
    }
}

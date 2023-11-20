package me.jishuna.jishlib.nms.nbt;

public interface NBTTag<T> {

    public TagType getType();

    public T getValue();
}

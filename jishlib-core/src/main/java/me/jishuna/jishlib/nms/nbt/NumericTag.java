package me.jishuna.jishlib.nms.nbt;

public interface NumericTag<T extends Number> extends NBTTag<T> {
    public byte getAsByte();

    public short getAsShort();

    public int getAsInt();

    public long getAsLong();

    public float getAsFloat();

    public double getAsDouble();
}

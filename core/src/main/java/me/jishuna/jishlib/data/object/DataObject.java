package me.jishuna.jishlib.data.object;

import java.io.DataOutput;
import java.io.IOException;

public abstract class DataObject<T> {
    protected String name = "";
    protected T value;

    protected DataObject(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T get() {
        return this.value;
    }

    public Object serialize() {
        return this.value;
    }

    public abstract byte getTagType();

    public abstract void write(DataOutput output) throws IOException;

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [name=" + this.name + ", value=" + this.value + "]";
    }

}

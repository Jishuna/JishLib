package me.jishuna.jishlib.data.object;

import java.io.IOException;
import me.jishuna.jishlib.data.source.DataWriter;

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

    public abstract void write(DataWriter writer) throws IOException;

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [name=" + this.name + ", value=" + this.value + "]";
    }

}

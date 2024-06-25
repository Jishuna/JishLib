package me.jishuna.jishlib.data.holder;

import java.io.IOException;
import me.jishuna.jishlib.data.HolderType;
import me.jishuna.jishlib.data.source.DataWriter;

public abstract class DataHolder<T> {
    protected String name = "";
    protected T value;

    protected DataHolder(String name, T value) {
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

    public abstract void write(DataWriter<?> writer) throws IOException;

    public abstract HolderType getType();

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [name=" + this.name + ", value=" + this.value + "]";
    }

}

package me.jishuna.jishlib.data.object;

public abstract class DataObject<T> {
    protected T value;

    protected DataObject(T value) {
        this.value = value;
    }

    public T get() {
        return this.value;
    }

    public Object serialize() {
        return this.value;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + this.value + "]";
    }
}

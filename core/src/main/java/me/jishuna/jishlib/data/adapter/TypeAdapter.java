package me.jishuna.jishlib.data.adapter;

import me.jishuna.jishlib.data.holder.DataHolder;

public interface TypeAdapter<T extends DataHolder<?>, R> {

    public Class<T> getObjectType();

    public R deserialize(T data);

    public T serialize(R value);

    public R fromString(String value);

    public String toString(R value);
}

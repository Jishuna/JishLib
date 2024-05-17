package me.jishuna.jishlib.data.adapter;

import me.jishuna.jishlib.data.object.DataObject;

public interface TypeAdapter<T extends DataObject<?>, R> {

    public Class<T> getObjectType();

    public R deserialize(T data);

    public T serialize(R value);
}

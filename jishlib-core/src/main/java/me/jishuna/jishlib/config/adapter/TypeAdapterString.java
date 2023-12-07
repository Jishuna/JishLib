package me.jishuna.jishlib.config.adapter;

public interface TypeAdapterString<S, R> extends TypeAdapter<S, R> {

    public R fromString(String value);

    public String toString(R value);
}

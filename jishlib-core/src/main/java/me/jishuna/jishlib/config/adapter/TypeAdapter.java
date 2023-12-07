package me.jishuna.jishlib.config.adapter;

public interface TypeAdapter<S, R> {

    public Class<S> getSavedType();

    public Class<R> getRuntimeType();

    public R read(S value);

    public S write(R value, S existing, boolean replace);
}

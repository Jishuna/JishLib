package me.jishuna.jishlib.config;

public interface ConfigSerializable<S> {
    public S serialize(S existing, boolean replace);
}

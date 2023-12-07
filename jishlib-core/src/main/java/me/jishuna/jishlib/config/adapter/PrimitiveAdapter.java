package me.jishuna.jishlib.config.adapter;

import java.util.function.Function;

public class PrimitiveAdapter<R> implements TypeAdapterString<R, R> {

    private final Class<R> clazz;
    private final Function<String, R> reader;

    public PrimitiveAdapter(Class<R> clazz, Function<String, R> reader) {
        this.clazz = clazz;
        this.reader = reader;
    }

    @Override
    public Class<R> getSavedType() {
        return this.clazz;
    }

    @Override
    public Class<R> getRuntimeType() {
        return this.clazz;
    }

    @Override
    public String toString(R value) {
        return String.valueOf(value);
    }

    @Override
    public R fromString(String value) {
        return this.reader.apply(value);
    }

    @Override
    public R read(R value) {
        return value;
    }

    @Override
    public R write(R value, R existing, boolean replace) {
        return value;
    }
}

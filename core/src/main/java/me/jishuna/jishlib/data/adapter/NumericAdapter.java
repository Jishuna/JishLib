package me.jishuna.jishlib.data.adapter;

import java.util.function.Function;
import me.jishuna.jishlib.data.holder.number.NumericDataHolder;

public class NumericAdapter<R extends Number> implements TypeAdapter<NumericDataHolder<R>, R> {
    private final Function<NumericDataHolder<R>, R> function;
    private final Function<String, R> reader;

    public NumericAdapter(Function<NumericDataHolder<R>, R> function, Function<String, R> reader) {
        this.function = function;
        this.reader = reader;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<NumericDataHolder<R>> getObjectType() {
        return (Class<NumericDataHolder<R>>) (Object) NumericDataHolder.class;
    }

    @Override
    public R deserialize(NumericDataHolder<R> data) {
        return this.function.apply(data);
    }

    @SuppressWarnings("unchecked")
    @Override
    public NumericDataHolder<R> serialize(R value) {
        return (NumericDataHolder<R>) NumericDataHolder.of(value);
    }

    @Override
    public R fromString(String value) {
        return this.reader.apply(value);
    }

    @Override
    public String toString(R value) {
        return String.valueOf(value);
    }

}

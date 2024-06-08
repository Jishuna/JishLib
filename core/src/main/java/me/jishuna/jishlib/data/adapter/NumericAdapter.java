package me.jishuna.jishlib.data.adapter;

import java.util.function.Function;
import me.jishuna.jishlib.data.object.NumericDataObject;

public class NumericAdapter<R extends Number> implements TypeAdapter<NumericDataObject<R>, R> {
    private final Function<NumericDataObject<R>, R> function;
    private final Function<String, R> reader;

    public NumericAdapter(Function<NumericDataObject<R>, R> function, Function<String, R> reader) {
        this.function = function;
        this.reader = reader;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<NumericDataObject<R>> getObjectType() {
        return (Class<NumericDataObject<R>>) (Object) NumericDataObject.class;
    }

    @Override
    public R deserialize(NumericDataObject<R> data) {
        return this.function.apply(data);
    }

    @Override
    public NumericDataObject<R> serialize(R value) {
        return NumericDataObject.of(value);
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

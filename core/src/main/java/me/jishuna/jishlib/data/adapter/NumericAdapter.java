package me.jishuna.jishlib.data.adapter;

import java.util.function.Function;
import me.jishuna.jishlib.data.object.PrimitiveDataObject;

public class NumericAdapter<R extends Number> implements TypeAdapterString<R> {
    private final Function<PrimitiveDataObject, R> function;
    private final Function<String, R> reader;

    public NumericAdapter(Function<PrimitiveDataObject, R> function, Function<String, R> reader) {
        this.function = function;
        this.reader = reader;
    }

    @Override
    public Class<PrimitiveDataObject> getObjectType() {
        return PrimitiveDataObject.class;
    }

    @Override
    public R deserialize(PrimitiveDataObject data) {
        return this.function.apply(data);
    }

    @Override
    public PrimitiveDataObject serialize(R value) {
        return PrimitiveDataObject.of(value);
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

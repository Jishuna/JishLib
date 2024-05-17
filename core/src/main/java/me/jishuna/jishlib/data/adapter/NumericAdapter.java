package me.jishuna.jishlib.data.adapter;

import java.util.function.Function;
import me.jishuna.jishlib.data.object.NumericDataObject;

public class NumericAdapter<R extends Number> implements TypeAdapter<NumericDataObject, R> {
    private final Function<NumericDataObject, R> function;

    public NumericAdapter(Function<NumericDataObject, R> function) {
        this.function = function;
    }

    @Override
    public Class<NumericDataObject> getObjectType() {
        return NumericDataObject.class;
    }

    @Override
    public R deserialize(NumericDataObject data) {
        return this.function.apply(data);
    }

    @Override
    public NumericDataObject serialize(R value) {
        return NumericDataObject.of(value);
    }

}

package me.jishuna.jishlib.data.adapter;

import me.jishuna.jishlib.data.holder.BooleanDataHolder;

public class BooleanAdapter implements TypeAdapter<BooleanDataHolder, Boolean> {

    @Override
    public Class<BooleanDataHolder> getObjectType() {
        return BooleanDataHolder.class;
    }

    @Override
    public Boolean deserialize(BooleanDataHolder data) {
        return data.get();
    }

    @Override
    public BooleanDataHolder serialize(Boolean value) {
        return BooleanDataHolder.of(value);
    }

    @Override
    public Boolean fromString(String value) {
        return Boolean.parseBoolean(value);
    }

    @Override
    public String toString(Boolean value) {
        return value.toString();
    }
}

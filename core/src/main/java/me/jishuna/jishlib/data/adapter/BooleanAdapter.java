package me.jishuna.jishlib.data.adapter;

import me.jishuna.jishlib.data.object.BooleanDataObject;

public class BooleanAdapter implements TypeAdapter<BooleanDataObject, Boolean> {

    @Override
    public Class<BooleanDataObject> getObjectType() {
        return BooleanDataObject.class;
    }

    @Override
    public Boolean deserialize(BooleanDataObject data) {
        return data.get();
    }

    @Override
    public BooleanDataObject serialize(Boolean value) {
        return BooleanDataObject.of(value);
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

package me.jishuna.jishlib.data.adapter;

import me.jishuna.jishlib.data.object.PrimitiveDataObject;

public class BooleanAdapter implements TypeAdapterString<Boolean> {

    @Override
    public Class<PrimitiveDataObject> getObjectType() {
        return PrimitiveDataObject.class;
    }

    @Override
    public Boolean deserialize(PrimitiveDataObject data) {
        return data.asBoolean();
    }

    @Override
    public PrimitiveDataObject serialize(Boolean value) {
        return PrimitiveDataObject.of(value);
    }

    @Override
    public Boolean fromString(String value) {
        return Boolean.parseBoolean(value);
    }

    @Override
    public String toString(Boolean value) {
        return String.valueOf(value);
    }

}

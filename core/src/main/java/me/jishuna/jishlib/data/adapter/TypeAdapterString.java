package me.jishuna.jishlib.data.adapter;

import me.jishuna.jishlib.data.object.PrimitiveDataObject;

public interface TypeAdapterString<R> extends TypeAdapter<PrimitiveDataObject, R> {

    @Override
    default Class<PrimitiveDataObject> getObjectType() {
        return PrimitiveDataObject.class;
    }

    @Override
    default R deserialize(PrimitiveDataObject data) {
        return fromString(data.asString());
    }

    @Override
    default PrimitiveDataObject serialize(R value) {
        return PrimitiveDataObject.of(toString(value));
    }

    public R fromString(String value);

    public String toString(R value);
}

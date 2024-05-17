package me.jishuna.jishlib.data.adapter;

import me.jishuna.jishlib.data.object.StringDataObject;

public interface TypeAdapterString<R> extends TypeAdapter<StringDataObject, R> {

    @Override
    default Class<StringDataObject> getObjectType() {
        return StringDataObject.class;
    }

    @Override
    default R deserialize(StringDataObject data) {
        return fromString(data.get());
    }

    @Override
    default StringDataObject serialize(R value) {
        return StringDataObject.of(toString(value));
    }

    public R fromString(String value);

    public String toString(R value);
}

package me.jishuna.jishlib.data.adapter;

import me.jishuna.jishlib.data.object.StringDataObject;

public class StringAdapter implements TypeAdapter<StringDataObject, String> {

    @Override
    public Class<StringDataObject> getObjectType() {
        return StringDataObject.class;
    }

    @Override
    public String deserialize(StringDataObject data) {
        return data.get();
    }

    @Override
    public StringDataObject serialize(String value) {
        return StringDataObject.of(value);
    }

    @Override
    public String fromString(String value) {
        return value;
    }

    @Override
    public String toString(String value) {
        return value;
    }
}

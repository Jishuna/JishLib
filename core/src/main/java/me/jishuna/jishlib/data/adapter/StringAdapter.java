package me.jishuna.jishlib.data.adapter;

import me.jishuna.jishlib.data.holder.StringDataHolder;

public class StringAdapter implements TypeAdapter<StringDataHolder, String> {

    @Override
    public Class<StringDataHolder> getObjectType() {
        return StringDataHolder.class;
    }

    @Override
    public String deserialize(StringDataHolder data) {
        return data.get();
    }

    @Override
    public StringDataHolder serialize(String value) {
        return StringDataHolder.of(value);
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

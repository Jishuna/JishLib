package me.jishuna.jishlib.data.adapter;

import me.jishuna.jishlib.data.holder.StringDataHolder;

public class CharacterAdapter implements TypeAdapter<StringDataHolder, Character> {

    @Override
    public Class<StringDataHolder> getObjectType() {
        return StringDataHolder.class;
    }

    @Override
    public Character deserialize(StringDataHolder data) {
        return fromString(data.get());
    }

    @Override
    public StringDataHolder serialize(Character value) {
        return StringDataHolder.of(toString(value));
    }

    @Override
    public Character fromString(String value) {
        return value.charAt(0);
    }

    @Override
    public String toString(Character value) {
        return Character.toString(value);
    }
}

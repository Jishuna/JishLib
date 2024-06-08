package me.jishuna.jishlib.data.adapter;

import me.jishuna.jishlib.data.object.StringDataObject;

public class CharacterAdapter implements TypeAdapter<StringDataObject, Character> {

    @Override
    public Class<StringDataObject> getObjectType() {
        return StringDataObject.class;
    }

    @Override
    public Character deserialize(StringDataObject data) {
        return fromString(data.get());
    }

    @Override
    public StringDataObject serialize(Character value) {
        return StringDataObject.of(toString(value));
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

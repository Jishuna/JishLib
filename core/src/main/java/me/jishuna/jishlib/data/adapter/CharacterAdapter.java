package me.jishuna.jishlib.data.adapter;

public class CharacterAdapter implements TypeAdapterString<Character> {

    @Override
    public Character fromString(String value) {
        return value.charAt(0);
    }

    @Override
    public String toString(Character value) {
        return Character.toString(value);
    }

}

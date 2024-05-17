package me.jishuna.jishlib.data.adapter;

public class StringAdapter implements TypeAdapterString<String> {

    @Override
    public String fromString(String value) {
        return value;
    }

    @Override
    public String toString(String value) {
        return value;
    }

}

package me.jishuna.jishlib.config.adapter;

public class StringTypeAdapter implements StringAdapter<String> {

    @Override
    public String toString(String value) {
        return value;
    }

    @Override
    public String fromString(String value) {
        return value;
    }
}

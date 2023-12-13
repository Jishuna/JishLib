package me.jishuna.jishlib.config.adapter;

public interface GenericStringAdapter<R> extends TypeAdapterString<String, R> {
    @Override
    default Class<String> getSavedType() {
        return String.class;
    }

    @Override
    default R read(String value) {
        return fromString(value);
    }

    @Override
    default String write(R value, String existing, boolean replace) {
        if (existing != null && !replace) {
            return null;
        }

        return toString(value);
    }
}

package me.jishuna.jishlib.config.adapter;

import me.jishuna.jishlib.util.StringUtils;

public class StringAdapter implements GenericStringAdapter<String> {

    @Override
    public Class<String> getRuntimeType() {
        return String.class;
    }

    @Override
    public String toString(String value) {
        return StringUtils.legacyToMiniMessage(value);
    }

    @Override
    public String fromString(String value) {
        return StringUtils.miniMessageToLegacy(value);
    }
}

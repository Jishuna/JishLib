package me.jishuna.jishlib.config.adapter;

import me.jishuna.jishlib.util.StringUtils;

public class StringTypeAdapter implements StringAdapter<String> {

    @Override
    public String toString(String value) {
        return StringUtils.legacyToMiniMessage(value);
    }

    @Override
    public String fromString(String value) {
        return StringUtils.miniMessageToLegacy(value);
    }
}

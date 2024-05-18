package me.jishuna.jishlib.data.adapter;

import net.kyori.adventure.text.Component;
import me.jishuna.jishlib.Constants;

public class ComponentAdapter implements TypeAdapterString<Component> {

    @Override
    public Component fromString(String value) {
        return Constants.MINI_MESSAGE.deserializeOrNull(value);
    }

    @Override
    public String toString(Component value) {
        return Constants.MINI_MESSAGE.serializeOrNull(value);
    }

}

package me.jishuna.jishlib.data.adapter;

import net.kyori.adventure.text.Component;
import me.jishuna.jishlib.Constants;
import me.jishuna.jishlib.data.holder.StringDataHolder;

public class ComponentAdapter implements TypeAdapter<StringDataHolder, Component> {

    @Override
    public Class<StringDataHolder> getObjectType() {
        return StringDataHolder.class;
    }

    @Override
    public Component deserialize(StringDataHolder data) {
        return fromString(data.get());
    }

    @Override
    public StringDataHolder serialize(Component value) {
        return StringDataHolder.of(toString(value));
    }

    @Override
    public Component fromString(String value) {
        return Constants.MINI_MESSAGE.deserializeOrNull(value);
    }

    @Override
    public String toString(Component value) {
        return Constants.MINI_MESSAGE.serializeOrNull(value);
    }

}

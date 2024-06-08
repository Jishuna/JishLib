package me.jishuna.jishlib.data.adapter;

import net.kyori.adventure.text.Component;
import me.jishuna.jishlib.Constants;
import me.jishuna.jishlib.data.object.StringDataObject;

public class ComponentAdapter implements TypeAdapter<StringDataObject, Component> {

    @Override
    public Class<StringDataObject> getObjectType() {
        return StringDataObject.class;
    }

    @Override
    public Component deserialize(StringDataObject data) {
        return fromString(data.get());
    }

    @Override
    public StringDataObject serialize(Component value) {
        return StringDataObject.of(toString(value));
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

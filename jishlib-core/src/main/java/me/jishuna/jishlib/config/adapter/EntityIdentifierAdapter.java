package me.jishuna.jishlib.config.adapter;

import me.jishuna.jishlib.entity.EntityIdentifier;
import me.jishuna.jishlib.entity.EntityIdentifiers;

public class EntityIdentifierAdapter implements GenericStringAdapter<EntityIdentifier> {

    @Override
    public Class<EntityIdentifier> getRuntimeType() {
        return EntityIdentifier.class;
    }

    @Override
    public String toString(EntityIdentifier value) {
        return value.toString();
    }

    @Override
    public EntityIdentifier fromString(String value) {
        return EntityIdentifiers.fromString(value);
    }
}

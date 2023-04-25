package me.jishuna.jishlib.pdc;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

public class BooleanType implements PersistentDataType<Byte, Boolean> {

    @Override
    public Class<Byte> getPrimitiveType() {
        return Byte.class;
    }

    @Override
    public Class<Boolean> getComplexType() {
        return Boolean.class;
    }

    @Override
    public Byte toPrimitive(Boolean complex, PersistentDataAdapterContext context) {
        return (byte) (Boolean.TRUE.equals(complex) ? 1 : 0);
    }

    @Override
    public Boolean fromPrimitive(Byte primitive, PersistentDataAdapterContext context) {
        return primitive == 1;
    }
}

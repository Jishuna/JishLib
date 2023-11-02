package me.jishuna.jishlib.pdc;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

public class VectorType implements PersistentDataType<Integer, Vector> {

    @Override
    public Class<Integer> getPrimitiveType() {
        return Integer.class;
    }

    @Override
    public Class<Vector> getComplexType() {
        return Vector.class;
    }

    @Override
    public Integer toPrimitive(Vector complex, PersistentDataAdapterContext context) {
        return ((byte) complex.getBlockX() & 0xff) << 16 | ((byte) complex.getBlockY() & 0xff) << 8 | ((byte) complex.getBlockZ() & 0xff);
    }

    @Override
    public Vector fromPrimitive(Integer primitive, PersistentDataAdapterContext context) {
        byte x = (byte) (primitive.intValue() >> 16);
        byte y = (byte) (primitive.intValue() >> 8);
        byte z = primitive.byteValue();

        return new Vector(x, y, z);
    }
}

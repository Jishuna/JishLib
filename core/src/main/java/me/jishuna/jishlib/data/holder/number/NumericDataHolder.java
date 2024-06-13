package me.jishuna.jishlib.data.holder.number;

import me.jishuna.jishlib.data.holder.DataHolder;

public abstract class NumericDataHolder<T extends Number> extends DataHolder<T> {
    protected NumericDataHolder(String name, T value) {
        super(name, value);
    }

    public static <T extends Number> NumericDataHolder<?> of(T number) {
        return of("", number);
    }

    public static <T extends Number> NumericDataHolder<?> of(String name, T number) {
        Class<?> clazz = number.getClass();

        if (clazz == Byte.class) {
            return ByteDataHolder.of(name, number.byteValue());
        }

        if (clazz == Short.class) {
            return ShortDataHolder.of(name, number.shortValue());
        }

        if (clazz == Integer.class) {
            return IntDataHolder.of(name, number.intValue());
        }

        if (clazz == Long.class) {
            return LongDataHolder.of(name, number.longValue());
        }

        if (clazz == Float.class) {
            return FloatDataHolder.of(name, number.floatValue());
        }

        return DoubleDataHolder.of(name, number.doubleValue());
    }

    public abstract byte byteValue();

    public abstract short shortValue();

    public abstract int intValue();

    public abstract long longValue();

    public abstract float floatValue();

    public abstract double doubleValue();

    public abstract boolean asBoolean();
}

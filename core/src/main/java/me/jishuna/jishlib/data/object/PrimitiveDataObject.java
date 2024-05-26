package me.jishuna.jishlib.data.object;

public class PrimitiveDataObject extends DataObject<Object> {

    private PrimitiveDataObject(Object value) {
        super(value);
    }

    public static PrimitiveDataObject of(Number value) {
        return new PrimitiveDataObject(value);
    }

    public static PrimitiveDataObject of(Boolean value) {
        return new PrimitiveDataObject(value);
    }

    public static PrimitiveDataObject of(String value) {
        return new PrimitiveDataObject(value);
    }

    public boolean isNumber() {
        return this.value instanceof Number;
    }

    public boolean isBoolean() {
        return this.value instanceof Boolean;
    }

    public byte asByte() {
        if (this.value instanceof Number num) {
            return num.byteValue();
        }
        return Byte.parseByte(String.valueOf(this.value));
    }

    public short asShort() {
        if (this.value instanceof Number num) {
            return num.shortValue();
        }
        return Short.parseShort(String.valueOf(this.value));
    }

    public int asInt() {
        if (this.value instanceof Number num) {
            return num.intValue();
        }
        return Integer.parseInt(String.valueOf(this.value));
    }

    public long asLong() {
        if (this.value instanceof Number num) {
            return num.longValue();
        }
        return Long.parseLong(String.valueOf(this.value));
    }

    public float asFloat() {
        if (this.value instanceof Number num) {
            return num.floatValue();
        }
        return Float.parseFloat(String.valueOf(this.value));
    }

    public double asDouble() {
        if (this.value instanceof Number num) {
            return num.doubleValue();
        }
        return Double.parseDouble(String.valueOf(this.value));
    }

    public boolean asBoolean() {
        if (this.value instanceof Boolean bool) {
            return bool;
        }

        return Boolean.parseBoolean(String.valueOf(this.value));
    }

    public String asString() {
        return String.valueOf(this.value);
    }
}

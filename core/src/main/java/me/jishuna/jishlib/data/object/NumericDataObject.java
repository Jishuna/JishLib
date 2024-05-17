package me.jishuna.jishlib.data.object;

public class NumericDataObject extends DataObject<Number> {

    private NumericDataObject(Number value) {
        super(value);
    }

    public static NumericDataObject of(Number value) {
        return new NumericDataObject(value);
    }

    public byte asByte() {
        return this.value.byteValue();
    }

    public short asShort() {
        return this.value.shortValue();
    }

    public int asInt() {
        return this.value.intValue();
    }

    public long asLong() {
        return this.value.longValue();
    }

    public float asFloat() {
        return this.value.floatValue();
    }

    public double asDouble() {
        return this.value.doubleValue();
    }
}

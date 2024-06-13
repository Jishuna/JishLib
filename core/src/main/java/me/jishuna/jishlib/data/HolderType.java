package me.jishuna.jishlib.data;

public enum HolderType {
    END(0), BYTE(1), SHORT(2), INT(3), LONG(4), FLOAT(5), DOUBLE(6),
    BYTE_ARRAY(7), STRING(8), LIST(9), MAP(10), INT_ARRAY(11), LONG_ARRAY(12);

    private final int id;

    HolderType(int id) {
        this.id = id;
    }

    public byte id() {
        return (byte) this.id;
    }
}

package me.jishuna.jishlib.util;

public class ChunkPosition {
    private final int x;
    private final int z;

    public ChunkPosition(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public int x() {
        return x;
    }

    public int z() {
        return z;
    }
}

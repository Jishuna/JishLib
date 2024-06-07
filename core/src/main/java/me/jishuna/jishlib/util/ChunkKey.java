package me.jishuna.jishlib.util;

import java.util.Objects;
import org.bukkit.Chunk;

public class ChunkKey {
    private final long key;

    private ChunkKey(long key) {
        this.key = key;
    }

    public static ChunkKey of(Chunk chunk) {
        return new ChunkKey(((long) chunk.getZ() << 32) | (chunk.getX() & 0xFFFFFFFFL));
    }

    public static ChunkKey of(int x, int z) {
        return new ChunkKey(((long) z << 32) | (x & 0xFFFFFFFFL));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.key);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ChunkKey other)) {
            return false;
        }
        return this.key == other.key;
    }
}

package me.jishuna.jishlib.util;

import java.util.Objects;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class BlockPos {
    public static final BlockPos ZERO = new BlockPos(0, 0, 0);
    public static final BlockPos ONE = new BlockPos(1, 1, 1);

    public final int x;
    public final int y;
    public final int z;

    public BlockPos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static BlockPos of(Location location) {
        return new BlockPos(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public static BlockPos of(Vector vector) {
        return new BlockPos(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
    }

    public BlockPos add(int x, int y, int z) {
        return new BlockPos(this.x + x, this.y + y, this.z + z);
    }

    public BlockPos add(BlockPos other) {
        return add(other.x, other.y, other.z);
    }

    public BlockPos add(Vector vector) {
        return add(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
    }

    public BlockPos subtract(int x, int y, int z) {
        return new BlockPos(this.x - x, this.y - y, this.z - z);
    }

    public BlockPos subtract(BlockPos other) {
        return subtract(other.x, other.y, other.z);
    }

    public BlockPos rotate(Rotation rotation) {
        return switch (rotation) {
        case NONE -> this;
        case CW_90 -> new BlockPos(-this.z, this.y, this.x);
        case CW_180 -> new BlockPos(-this.x, this.y, -this.z);
        case CW_270 -> new BlockPos(this.z, this.y, -this.x);
        };
    }

    public BlockPos inverse() {
        return new BlockPos(-this.x, -this.y, -this.z);
    }

    public Vector toVector() {
        return new Vector(this.x, this.y, this.z);
    }

    public Location toLocation(World world) {
        return new Location(world, this.x, this.y, this.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y, this.z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BlockPos other)) {
            return false;
        }
        return this.x == other.x && this.y == other.y && this.z == other.z;
    }

    @Override
    public String toString() {
        return "BlockPos [x=" + this.x + ", y=" + this.y + ", z=" + this.z + "]";
    }
}

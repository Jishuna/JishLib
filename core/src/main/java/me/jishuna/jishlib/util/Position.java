package me.jishuna.jishlib.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.util.NumberConversions;

import java.util.Objects;

public sealed class Position {
    public static final Position ZERO = new Position(0, 0, 0);
    public static final Position ONE = new Position(1, 1, 1);

    public static Position of(Location location) {
        return new Position(location.getX(), location.getY(), location.getZ());
    }

    protected double x;
    protected double y;
    protected double z;

    public Position(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Position(double value) {
        this.x = value;
        this.y = value;
        this.z = value;
    }

    public Position set(double x, double y, double z) {
        return new Position(x, y, z);
    }

    public Position set(int x, int y, int z) {
        return new Position(x, y, z);
    }

    public Position add(double x, double y, double z) {
        return set(this.x + x, this.y + y, this.z + z);
    }

    public Position add(int x, int y, int z) {
        return set(this.x + x, this.y + y, this.z + z);
    }

    public Position add(Position other) {
        return set(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public Position sub(double x, double y, double z) {
        return set(this.x - x, this.y - y, this.z - z);
    }

    public Position sub(int x, int y, int z) {
        return set(this.x - x, this.y - y, this.z - z);
    }

    public Position sub(Position other) {
        return set(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public Position relative(Direction direction) {
        BlockFace face = direction.blockFace();
        return set(x + face.getModX(), y + face.getModY(), z + face.getModZ());
    }

    public Position transform(Rotation rotation) {
        return switch (rotation) {
            case COUNTERCLOCKWISE_90 -> set(z, y, -x);
            case CLOCKWISE_90 -> set(-z, y, x);
            case CLOCKWISE_180 -> set(-x, y, -z);
            default -> this;
        };
    }

    public Position transform(Rotation rotation, Position pivot) {
        double pivotX = pivot.x();
        double pivotZ = pivot.z();

        return switch (rotation) {
            case COUNTERCLOCKWISE_90 -> set(pivotX - pivotZ + z, y, pivotX + pivotZ - x);
            case CLOCKWISE_90 -> set(pivotX + pivotZ - z, y, pivotZ - pivotX + x);
            case CLOCKWISE_180 -> set(pivotX + pivotX - x, y, pivotZ + pivotZ - z);
            default -> this;
        };
    }

    public Location toLocation(World world) {
        return new Location(world, x, y, z);
    }

    public ChunkPosition toChunkPosition() {
        return new ChunkPosition(blockX() >> 4, blockZ() >> 4);
    }

    public Mutable mutable() {
        return new Mutable(x, y, z);
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }

    public int blockX() {
        return NumberConversions.floor(x);
    }

    public int blockY() {
        return NumberConversions.floor(y);
    }

    public int blockZ() {
        return NumberConversions.floor(z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position position)) return false;
        return Double.compare(x, position.x) == 0 && Double.compare(y, position.y) == 0 && Double.compare(z, position.z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "Position[" + x + ", " + y + ", " + z + "]";
    }

    public static final class Mutable extends Position {

        public Mutable(double x, double y, double z) {
            super(x, y, z);
        }

        public Mutable(int x, int y, int z) {
            super(x, y, z);
        }

        public Mutable(double value) {
            super(value);
        }

        public Position set(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
            return this;
        }

        public Position set(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
            return this;
        }

        @Override
        public Mutable mutable() {
            return this;
        }

        public Position immutable() {
            return new Position(x, y, z);
        }
    }
}

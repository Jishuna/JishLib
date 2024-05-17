package me.jishuna.jishlib.util;

import java.util.Objects;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.NumberConversions;
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

    public BlockPos rotate(Rotation rotation, double xOrigin, double zOrigin) {
        double radians = rotation.getRadians();

        double angleCos = Math.cos(radians);
        double angleSin = Math.sin(radians);

        double x = this.x + xOrigin;
        double z = this.z + zOrigin;

        double x2 = angleCos * x + angleSin * z;
        double z2 = -angleSin * x + angleCos * z;

        return new BlockPos(NumberConversions.round(x2 - xOrigin), this.y, NumberConversions.round(z2 - zOrigin));
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

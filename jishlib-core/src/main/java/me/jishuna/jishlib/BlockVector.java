package me.jishuna.jishlib;

import java.util.Objects;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class BlockVector {

    private final int x;
    private final int y;
    private final int z;

    public BlockVector(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static BlockVector fromBukkit(Vector vector) {
        int x = vector.getBlockX();
        int y = vector.getBlockY();
        int z = vector.getBlockZ();

        return new BlockVector(x, y, z);
    }

    public static BlockVector fromLocation(Location location) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        return new BlockVector(x, y, z);
    }

    public Location toLocation(World world) {
        return new Location(world, this.x, this.y, this.z);
    }

    public Vector toVector() {
        return new Vector(this.x, this.y, this.z);
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
        if (!(obj instanceof BlockVector other)) {
            return false;
        }
        return this.x == other.x && this.y == other.y && this.z == other.z;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("BlockVector[")
                .append(this.x)
                .append(", ")
                .append(this.y)
                .append(", ")
                .append(this.z)
                .append("]")
                .toString();
    }
}

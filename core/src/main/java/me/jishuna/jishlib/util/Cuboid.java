package me.jishuna.jishlib.util;

import java.util.Iterator;
import java.util.Objects;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Cuboid implements Iterable<BlockPos> {
    public final double minX;
    public final double minY;
    public final double minZ;
    public final double maxX;
    public final double maxY;
    public final double maxZ;

    private Cuboid(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public static Cuboid of(double x1, double y1, double z1, double x2, double y2, double z2) {
        double minX = Math.min(x1, x2);
        double minY = Math.min(y1, y2);
        double minZ = Math.min(z1, z2);
        double maxX = Math.max(x1, x2);
        double maxY = Math.max(y1, y2);
        double maxZ = Math.max(z1, z2);

        return new Cuboid(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public static Cuboid of(BlockPos corner1, BlockPos corner2) {
        int minX = Math.min(corner1.x, corner2.x);
        int minY = Math.min(corner1.y, corner2.y);
        int minZ = Math.min(corner1.z, corner2.z);
        int maxX = Math.max(corner1.x, corner2.x) + 1;
        int maxY = Math.max(corner1.y, corner2.y) + 1;
        int maxZ = Math.max(corner1.z, corner2.z) + 1;

        return new Cuboid(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public boolean intersects(Cuboid other) {
        return intersects(other.minX, other.minY, other.minZ, other.maxX, other.maxY, other.maxZ);
    }

    public void outline(Player player, Color color) {
        Vector pointA = new Vector(this.minX, this.minY, this.minZ);
        Vector pointB = new Vector(this.maxX, this.minY, this.maxZ);
        Vector pointC = new Vector(this.maxX, this.maxY, this.minZ);
        Vector pointD = new Vector(this.minX, this.maxY, this.maxZ);

        Vector sizeX = new Vector(getWidthX(), 0, 0);
        Vector sizeY = new Vector(0, getHeight(), 0);
        Vector sizeZ = new Vector(0, 0, getWidthZ());

        drawParticleLines(player, sizeX, 0.5, color, pointA, pointD);
        drawParticleLines(player, sizeY, 0.5, color, pointA, pointB);
        drawParticleLines(player, sizeZ, 0.5, color, pointA, pointC);

        drawParticleLines(player, sizeX.clone().multiply(-1), 0.5, color, pointB, pointC);
        drawParticleLines(player, sizeY.clone().multiply(-1), 0.5, color, pointC, pointD);
        drawParticleLines(player, sizeZ.clone().multiply(-1), 0.5, color, pointB, pointD);
    }

    public double getWidthX() {
        return (this.maxX - this.minX);
    }

    public double getWidthZ() {
        return (this.maxZ - this.minZ);
    }

    public double getHeight() {
        return (this.maxY - this.minY);
    }

    public long getVolume() {
        return (long) Math.ceil(getWidthX() * getHeight() * getWidthZ());
    }

    @Override
    public Iterator<BlockPos> iterator() {
        return new CuboidIterator(this);
    }

    private boolean intersects(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return this.minX < maxX && this.maxX > minX
                && this.minY < maxY && this.maxY > minY
                && this.minZ < maxZ && this.maxZ > minZ;
    }

    private void drawParticleLines(Player player, Vector path, double spacing, Color color, Vector... origins) {
        for (Vector origin : origins) {
            for (double distance = 0; distance <= path.length(); distance += spacing) {
                Vector position = origin.clone().add(path.clone().normalize().multiply(distance));

                player.spawnParticle(Particle.DUST, position.toLocation(player.getWorld()), 1, new DustOptions(color, 1));
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.maxX, this.maxY, this.maxZ, this.minX, this.minY, this.minZ);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Cuboid other)) {
            return false;
        }
        return Double.doubleToLongBits(this.maxX) == Double.doubleToLongBits(other.maxX)
                && Double.doubleToLongBits(this.maxY) == Double.doubleToLongBits(other.maxY)
                && Double.doubleToLongBits(this.maxZ) == Double.doubleToLongBits(other.maxZ)
                && Double.doubleToLongBits(this.minX) == Double.doubleToLongBits(other.minX)
                && Double.doubleToLongBits(this.minY) == Double.doubleToLongBits(other.minY)
                && Double.doubleToLongBits(this.minZ) == Double.doubleToLongBits(other.minZ);
    }

    @Override
    public String toString() {
        return "Cuboid [minX=" + this.minX + ", minY=" + this.minY + ", minZ=" + this.minZ + ", maxX=" + this.maxX + ", maxY=" + this.maxY + ", maxZ=" + this.maxZ + "]";
    }

    private static class CuboidIterator implements Iterator<BlockPos> {

        private final Cuboid cuboid;
        private int index;

        public CuboidIterator(Cuboid cuboid) {
            this.cuboid = cuboid;
        }

        @Override
        public boolean hasNext() {
            return this.index < this.cuboid.getVolume();
        }

        @Override
        public BlockPos next() {
            int x = (int) (this.index % this.cuboid.getWidthX() + this.cuboid.minX);
            int y = (int) (this.index / this.cuboid.getWidthX() % this.cuboid.getHeight() + this.cuboid.minY);
            int z = (int) (this.index / this.cuboid.getWidthX() / this.cuboid.getHeight() + this.cuboid.minZ);

            this.index++;

            return new BlockPos(x, y, z);
        }
    }
}

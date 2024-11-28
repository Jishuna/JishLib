package me.jishuna.jishlib.util;

import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class VoxelShape {
    private final Position.Mutable minimumCorner = new Position.Mutable(Double.MAX_VALUE);
    private final Position.Mutable maximumCorner = new Position.Mutable(Double.MIN_VALUE);
    private final Set<Cuboid> components = new HashSet<>();

    public boolean intersects(Cuboid cuboid) {
        for (Cuboid component : this.components) {
            if (component.intersects(cuboid)) {
                return true;
            }
        }
        return false;
    }

    public VoxelShape merge(Cuboid cuboid) {
        this.components.add(cuboid);

        minimumCorner.set(Math.min(minimumCorner.x(), cuboid.minX()), Math.min(minimumCorner.y(), cuboid.minY()), Math.min(minimumCorner.z(), cuboid.minZ()));
        maximumCorner.set(Math.max(maximumCorner.x(), cuboid.maxX()), Math.max(maximumCorner.y(), cuboid.maxY()), Math.max(maximumCorner.z(), cuboid.maxZ()));
        return this;
    }

    public void outline(Player player, Color color) {
        for (Cuboid component : this.components) {
            component.outline(player, color);
        }
    }

    public Position getMinimumCorner() {
        return minimumCorner.immutable();
    }

    public Position getMaximumCorner() {
        return maximumCorner.immutable();
    }

    @Override
    public String toString() {
        return "VoxelShape [components=" + this.components + "]";
    }
}

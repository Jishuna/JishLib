package me.jishuna.jishlib.util;

import org.bukkit.Axis;
import org.bukkit.block.BlockFace;

import java.util.EnumMap;
import java.util.Map;

public enum Direction {
    UP(BlockFace.UP, Axis.Y),
    DOWN(BlockFace.DOWN, Axis.Y),
    NORTH(BlockFace.NORTH, Axis.Z),
    SOUTH(BlockFace.SOUTH, Axis.Z),
    EAST(BlockFace.EAST, Axis.X),
    WEST(BlockFace.WEST, Axis.X);

    private static final Map<BlockFace, Direction> blockFaceLookup = new EnumMap<>(BlockFace.class);

    static {
        for (Direction direction : values()) {
            blockFaceLookup.put(direction.blockFace, direction);
        }
    }

    public static Direction fromBlockFace(BlockFace blockFace) {
        return blockFaceLookup.get(blockFace);
    }

    private final BlockFace blockFace;
    private final Axis axis;

    Direction(BlockFace blockFace, Axis axis) {
        this.blockFace = blockFace;
        this.axis = axis;
    }

    public BlockFace blockFace() {
        return blockFace;
    }

    public Axis axis() {
        return axis;
    }

    public Direction getOpposite() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
        };
    }

    public Direction rotateClockwiseY() {
        return switch (this) {
            case NORTH -> EAST;
            case SOUTH -> WEST;
            case EAST -> SOUTH;
            case WEST -> NORTH;
            default -> this;
        };
    }

    public Direction rotateCounterClockwiseY() {
        return switch (this) {
            case NORTH -> WEST;
            case SOUTH -> EAST;
            case EAST -> NORTH;
            case WEST -> SOUTH;
            default -> this;
        };
    }
}

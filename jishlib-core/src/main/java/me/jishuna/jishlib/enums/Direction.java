package me.jishuna.jishlib.enums;

import java.util.EnumMap;
import java.util.Map;
import org.bukkit.Axis;
import org.bukkit.block.BlockFace;

public enum Direction {
    NONE(0, null, null), UP(2, BlockFace.UP, Axis.Y), DOWN(1, BlockFace.DOWN, Axis.Y), NORTH(4, BlockFace.NORTH, Axis.Z), SOUTH(3, BlockFace.SOUTH, Axis.Z), EAST(6, BlockFace.EAST, Axis.X), WEST(5, BlockFace.WEST, Axis.X);

    private final Direction[] opposites = new Direction[7];
    private static final Map<BlockFace, Direction> bukkitMap = new EnumMap<>(BlockFace.class);

    static {
        for (Direction direction : values()) {
            if (direction != NONE) {
                bukkitMap.put(direction.blockFace, direction);
            }
        }
    }

    private final int oppositeIndex;
    private final BlockFace blockFace;
    private final Axis axis;

    private Direction(int oppositeIndex, BlockFace blockface, Axis axis) {
        this.oppositeIndex = oppositeIndex;
        this.blockFace = blockface;
        this.axis = axis;
    }

    public static Direction fromBlockFace(BlockFace face) {
        return bukkitMap.get(face);
    }

    public BlockFace getBlockFace() {
        return this.blockFace;
    }

    public Axis getAxis() {
        return this.axis;
    }

    public boolean isVertical() {
        return this.axis == Axis.Y;
    }

    public boolean isHorizontal() {
        return this.axis == Axis.X || this.axis == Axis.Z;
    }

    public Direction getOpposite() {
        Direction direction = this.opposites[ordinal()];
        if (direction == null) {
            direction = Direction.values()[this.oppositeIndex];
            this.opposites[ordinal()] = direction;
        }

        return direction;
    }

    public Direction rotateClockwise(Axis axis) {
        return switch (axis) {
        case X -> rotateClockwiseX();
        case Y -> rotateClockwiseY();
        case Z -> rotateClockwiseZ();
        };
    }

    public Direction rotateCounterClockwise(Axis axis) {
        return switch (axis) {
        case X -> rotateCounterClockwiseX();
        case Y -> rotateCounterClockwiseY();
        case Z -> rotateCounterClockwiseZ();
        };
    }

    public Direction rotateClockwiseX() {
        return switch (this) {
        case UP -> NORTH;
        case NORTH -> DOWN;
        case DOWN -> SOUTH;
        case SOUTH -> UP;
        default -> this;
        };
    }

    public Direction rotateCounterClockwiseX() {
        return switch (this) {
        case UP -> SOUTH;
        case NORTH -> UP;
        case DOWN -> NORTH;
        case SOUTH -> DOWN;
        default -> this;
        };
    }

    public Direction rotateClockwiseY() {
        return switch (this) {
        case NORTH -> EAST;
        case EAST -> SOUTH;
        case SOUTH -> WEST;
        case WEST -> NORTH;
        default -> this;
        };
    }

    public Direction rotateCounterClockwiseY() {
        return switch (this) {
        case NORTH -> WEST;
        case EAST -> NORTH;
        case SOUTH -> EAST;
        case WEST -> SOUTH;
        default -> this;
        };
    }

    public Direction rotateClockwiseZ() {
        return switch (this) {
        case UP -> EAST;
        case EAST -> DOWN;
        case DOWN -> WEST;
        case WEST -> UP;
        default -> this;
        };
    }

    public Direction rotateCounterClockwiseZ() {
        return switch (this) {
        case UP -> WEST;
        case WEST -> DOWN;
        case DOWN -> EAST;
        case EAST -> UP;
        default -> this;
        };
    }

}

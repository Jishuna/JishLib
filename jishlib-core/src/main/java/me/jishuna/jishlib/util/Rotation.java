package me.jishuna.jishlib.util;

import org.joml.Math;
import me.jishuna.jishlib.enums.Direction;

public enum Rotation {
    NONE(0), CW_90(270), CW_180(180), CW_270(90);

    private final int degrees;
    private final float radians;

    private Rotation(int degrees) {
        this.degrees = degrees;
        this.radians = Math.toRadians(degrees);
    }

    public int getDegrees() {
        return this.degrees;
    }

    public float getRadians() {
        return this.radians;
    }

    public Direction apply(Direction direction) {
        return switch (this) {
        case NONE -> direction;
        case CW_90 -> rotate90(direction);
        case CW_180 -> rotate180(direction);
        case CW_270 -> rotate270(direction);
        };
    }

    private Direction rotate90(Direction direction) {
        return switch (direction) {
        case NORTH -> Direction.EAST;
        case EAST -> Direction.SOUTH;
        case SOUTH -> Direction.WEST;
        default -> Direction.NORTH;
        };
    }

    private Direction rotate180(Direction direction) {
        return switch (direction) {
        case NORTH -> Direction.SOUTH;
        case EAST -> Direction.WEST;
        case SOUTH -> Direction.NORTH;
        default -> Direction.EAST;
        };
    }

    private Direction rotate270(Direction direction) {
        return switch (direction) {
        case NORTH -> Direction.WEST;
        case EAST -> Direction.NORTH;
        case SOUTH -> Direction.EAST;
        default -> Direction.SOUTH;
        };
    }
}

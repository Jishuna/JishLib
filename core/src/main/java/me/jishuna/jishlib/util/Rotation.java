package me.jishuna.jishlib.util;

import org.joml.Math;

public enum Rotation {
    NONE(0, 0, 0), CW_90(270, 1, 0), CW_180(180, 1, 1), CW_270(90, 0, 1);

    private final int degrees;
    private final float radians;
    private final int xOffset;
    private final int zOffset;

    private Rotation(int degrees, int xOffset, int zOffset) {
        this.degrees = degrees;
        this.radians = Math.toRadians(degrees);
        this.xOffset = xOffset;
        this.zOffset = zOffset;
    }

    public int getDegrees() {
        return this.degrees;
    }

    public float getRadians() {
        return this.radians;
    }

    public int getXOffset() {
        return this.xOffset;
    }

    public int getZOffset() {
        return this.zOffset;
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

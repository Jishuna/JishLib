package me.jishuna.jishlib.util;

import org.bukkit.block.structure.StructureRotation;

public enum Rotation {
    NONE(StructureRotation.NONE),
    CLOCKWISE_90(StructureRotation.CLOCKWISE_90),
    CLOCKWISE_180(StructureRotation.CLOCKWISE_180),
    COUNTERCLOCKWISE_90(StructureRotation.COUNTERCLOCKWISE_90);

    private final StructureRotation structureRotation;

    Rotation(StructureRotation structureRotation) {
        this.structureRotation = structureRotation;
    }

    public StructureRotation structureRotation() {
        return structureRotation;
    }
}

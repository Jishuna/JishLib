package me.jishuna.jishlib.entity;

import java.util.Objects;
import org.bukkit.DyeColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.material.Colorable;

public class ColoredEntityIdentifier extends EntityIdentifier {
    private final DyeColor color;

    public ColoredEntityIdentifier(Colorable entity) {
        this(((Entity) entity).getType(), entity.getColor());
    }

    public ColoredEntityIdentifier(EntityType type, DyeColor color) {
        super(type);

        this.color = color;
    }

    @Override
    public String toString() {
        String colorString;
        if (this.color == null) {
            colorString = "none";
        } else {
            colorString = this.color.toString().toLowerCase();
        }

        return new StringBuilder(super.toString())
                .append("[color=")
                .append(colorString)
                .append("]")
                .toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(this.color);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj) || !(obj instanceof ColoredEntityIdentifier other)) {
            return false;
        }
        return this.color == other.color;
    }
}

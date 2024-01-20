package me.jishuna.jishlib.entity;

import java.util.Objects;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;

public class HorseIdentifier extends EntityIdentifier {
    private final Horse.Color color;
    private final Horse.Style style;

    public HorseIdentifier(Horse entity) {
        this(entity.getColor(), entity.getStyle());
    }

    public HorseIdentifier(Horse.Color color, Horse.Style style) {
        super(EntityType.HORSE);

        this.color = color;
        this.style = style;
    }

    @Override
    public String toString() {
        return new StringBuilder(super.toString())
                .append("[color=")
                .append(this.color.toString().toLowerCase())
                .append(",style=")
                .append(this.style.toString().toLowerCase())
                .append("]")
                .toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(this.color, this.style);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj) || !(obj instanceof HorseIdentifier other)) {
            return false;
        }
        return this.color == other.color && this.style == other.style;
    }
}

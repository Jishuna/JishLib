package me.jishuna.jishlib.entity;

import java.util.Objects;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fox;

public class FoxIdentifier extends EntityIdentifier {
    private final Fox.Type type;

    public FoxIdentifier(Fox entity) {
        this(entity.getFoxType());
    }

    public FoxIdentifier(Fox.Type type) {
        super(EntityType.FOX);

        this.type = type;
    }

    @Override
    public String toString() {
        return new StringBuilder(super.toString())
                .append("[type=")
                .append(this.type.toString().toLowerCase())
                .append("]")
                .toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(this.type);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj) || !(obj instanceof FoxIdentifier other)) {
            return false;
        }
        return this.type == other.type;
    }
}

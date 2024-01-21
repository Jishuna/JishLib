package me.jishuna.jishlib.entity;

import java.util.Objects;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Frog;

public class FrogIdentifier extends EntityIdentifier {
    private final Frog.Variant variant;

    public FrogIdentifier(Frog entity) {
        this(entity.getVariant());
    }

    public FrogIdentifier(Frog.Variant variant) {
        super(EntityType.FROG);

        this.variant = variant;
    }

    @Override
    public String toString() {
        return new StringBuilder(super.toString())
                .append("[variant=")
                .append(this.variant.toString().toLowerCase())
                .append("]")
                .toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(this.variant);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj) || !(obj instanceof FrogIdentifier other)) {
            return false;
        }
        return this.variant == other.variant;
    }
}

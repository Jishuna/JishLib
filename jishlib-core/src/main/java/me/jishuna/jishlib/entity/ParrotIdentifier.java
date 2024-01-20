package me.jishuna.jishlib.entity;

import java.util.Objects;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Parrot;

public class ParrotIdentifier extends EntityIdentifier {
    private final Parrot.Variant variant;

    public ParrotIdentifier(Parrot entity) {
        this(entity.getVariant());
    }

    public ParrotIdentifier(Parrot.Variant variant) {
        super(EntityType.PARROT);

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
        if (!super.equals(obj) || !(obj instanceof ParrotIdentifier other)) {
            return false;
        }
        return this.variant == other.variant;
    }
}

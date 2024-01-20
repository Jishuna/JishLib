package me.jishuna.jishlib.entity;

import java.util.Objects;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.MushroomCow;

public class MooshroomIdentifier extends EntityIdentifier {
    private final MushroomCow.Variant variant;

    public MooshroomIdentifier(MushroomCow entity) {
        this(entity.getVariant());
    }

    public MooshroomIdentifier(MushroomCow.Variant variant) {
        super(EntityType.MUSHROOM_COW);

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
        if (!super.equals(obj) || !(obj instanceof MooshroomIdentifier other)) {
            return false;
        }
        return this.variant == other.variant;
    }
}

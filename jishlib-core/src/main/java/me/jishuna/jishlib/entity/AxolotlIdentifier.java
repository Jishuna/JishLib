package me.jishuna.jishlib.entity;

import java.util.Objects;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.EntityType;

public class AxolotlIdentifier extends EntityIdentifier {
    private final Axolotl.Variant variant;

    public AxolotlIdentifier(Axolotl entity) {
        this(entity.getVariant());
    }

    public AxolotlIdentifier(Axolotl.Variant variant) {
        super(EntityType.AXOLOTL);

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
        if (!super.equals(obj) || !(obj instanceof AxolotlIdentifier other)) {
            return false;
        }
        return this.variant == other.variant;
    }
}

package me.jishuna.jishlib.entity;

import java.util.Objects;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;

public class CatIdentifier extends EntityIdentifier {
    private final Cat.Type catType;

    public CatIdentifier(Cat entity) {
        this(entity.getCatType());
    }

    public CatIdentifier(Cat.Type catType) {
        super(EntityType.CAT);

        this.catType = catType;
    }

    @Override
    public String toString() {
        return new StringBuilder(super.toString())
                .append("[type=")
                .append(this.catType.toString().toLowerCase())
                .append("]")
                .toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(this.catType);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj) || !(obj instanceof CatIdentifier other)) {
            return false;
        }
        return this.catType == other.catType;
    }
}

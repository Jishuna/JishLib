package me.jishuna.jishlib.entity;

import java.util.Objects;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class EntityIdentifier {
    protected final EntityType type;

    public EntityIdentifier(Entity entity) {
        this(entity.getType());
    }

    public EntityIdentifier(EntityType type) {
        this.type = type;
    }

    public EntityType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return this.type.getKey().getKey();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EntityIdentifier other)) {
            return false;
        }
        return this.type == other.type;
    }
}

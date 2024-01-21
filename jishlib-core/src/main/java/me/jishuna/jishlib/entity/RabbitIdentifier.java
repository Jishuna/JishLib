package me.jishuna.jishlib.entity;

import java.util.Objects;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Rabbit;

public class RabbitIdentifier extends EntityIdentifier {
    private final Rabbit.Type rabbitType;

    public RabbitIdentifier(Rabbit entity) {
        this(entity.getRabbitType());
    }

    public RabbitIdentifier(Rabbit.Type rabbitType) {
        super(EntityType.RABBIT);

        this.rabbitType = rabbitType;
    }

    @Override
    public String toString() {
        return new StringBuilder(super.toString())
                .append("[type=")
                .append(this.rabbitType.toString().toLowerCase())
                .append("]")
                .toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(this.rabbitType);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj) || !(obj instanceof RabbitIdentifier other)) {
            return false;
        }
        return this.rabbitType == other.rabbitType;
    }
}

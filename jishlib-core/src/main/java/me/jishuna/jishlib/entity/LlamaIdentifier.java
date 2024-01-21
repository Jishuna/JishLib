package me.jishuna.jishlib.entity;

import java.util.Objects;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Llama;

public class LlamaIdentifier extends EntityIdentifier {
    private final Llama.Color color;

    public LlamaIdentifier(Llama entity) {
        this(entity.getType(), entity.getColor());
    }

    public LlamaIdentifier(EntityType type, Llama.Color color) {
        super(type);

        this.color = color;
    }

    @Override
    public String toString() {
        return new StringBuilder(super.toString())
                .append("[color=")
                .append(this.color.toString().toLowerCase())
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
        if (!super.equals(obj) || !(obj instanceof LlamaIdentifier other)) {
            return false;
        }
        return this.color == other.color;
    }
}

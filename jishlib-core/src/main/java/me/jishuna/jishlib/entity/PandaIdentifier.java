package me.jishuna.jishlib.entity;

import java.util.Objects;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Panda;

public class PandaIdentifier extends EntityIdentifier {
    private final Panda.Gene pandaType;

    public PandaIdentifier(Panda entity) {
        this(entity.getMainGene());
    }

    public PandaIdentifier(Panda.Gene pandaType) {
        super(EntityType.PANDA);

        this.pandaType = pandaType;
    }

    @Override
    public String toString() {
        return new StringBuilder(super.toString())
                .append("[type=")
                .append(this.pandaType.toString().toLowerCase())
                .append("]")
                .toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(this.pandaType);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj) || !(obj instanceof PandaIdentifier other)) {
            return false;
        }
        return this.pandaType == other.pandaType;
    }
}

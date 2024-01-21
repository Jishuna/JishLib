package me.jishuna.jishlib.entity;

import java.util.Objects;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.entity.ZombieVillager;

public class VillagerIdentifier extends EntityIdentifier {
    private final Villager.Profession profession;
    private final Villager.Type villagerType;

    public VillagerIdentifier(Villager entity) {
        this(entity.getType(), entity.getProfession(), entity.getVillagerType());
    }

    public VillagerIdentifier(ZombieVillager entity) {
        this(entity.getType(), entity.getVillagerProfession(), entity.getVillagerType());
    }

    public VillagerIdentifier(EntityType type, Villager.Profession profession, Villager.Type villagerType) {
        super(type);

        this.profession = profession;
        this.villagerType = villagerType;
    }

    @Override
    public String toString() {
        return new StringBuilder(super.toString())
                .append("[profession=")
                .append(this.profession.toString().toLowerCase())
                .append(",type=")
                .append(this.villagerType.toString().toLowerCase())
                .append("]")
                .toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(this.profession, this.villagerType);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj) || !(obj instanceof VillagerIdentifier other)) {
            return false;
        }
        return this.profession == other.profession && this.villagerType == other.villagerType;
    }
}

package me.jishuna.jishlib.nms.entity.goal;

import org.bukkit.entity.EntityType;

public class AttackTargetGoal implements DefaultGoal {
    private final EntityType type;
    private final boolean checkVisibility;

    public AttackTargetGoal(EntityType type, boolean checkVisibility) {
        this.type = type;
        this.checkVisibility = checkVisibility;
    }

    public EntityType getType() {
        return this.type;
    }

    public boolean isCheckVisibility() {
        return this.checkVisibility;
    }
}

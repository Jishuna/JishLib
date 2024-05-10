package me.jishuna.jishlib.nms.v1_20_R4;

import org.bukkit.Sound;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftZombie;
import me.jishuna.jishlib.nms.entity.CustomEntity;
import me.jishuna.jishlib.nms.entity.goal.DefaultGoal;

public class CraftCustomEntity extends CraftZombie implements CustomEntity {
    private final InternalCustomEntity entity;

    public CraftCustomEntity(CraftServer server, InternalCustomEntity entity) {
        super(server, entity);
        this.entity = entity;
    }

    @Override
    public void addGoal(int priority, DefaultGoal goal) {
        this.entity.addGoal(priority, goal);
    }

    @Override
    public void setAmbientSound(Sound sound) {
        this.entity.setAmbientSound(sound);
    }

    @Override
    public void setDamageSound(Sound sound) {
        this.entity.setHurtSound(sound);
    }

    @Override
    public void setDeathSound(Sound sound) {
        this.entity.setDeathSound(sound);
    }

    @Override
    public void setStepSound(Sound sound) {
        this.entity.setStepSound(sound);
    }
}

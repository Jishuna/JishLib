package me.jishuna.jishlib.nms.entity;

import org.bukkit.Sound;
import org.bukkit.entity.Creature;
import me.jishuna.jishlib.nms.entity.goal.DefaultGoal;

public interface CustomEntity extends Creature {

    public void addGoal(int priority, DefaultGoal goal);

    public void setAmbientSound(Sound sound);

    public void setDamageSound(Sound sound);

    public void setDeathSound(Sound sound);

    public void setStepSound(Sound sound);

}

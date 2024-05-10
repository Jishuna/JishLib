package me.jishuna.jishlib.nms.v1_20_R4;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import org.bukkit.craftbukkit.entity.CraftEntityType;
import me.jishuna.jishlib.nms.entity.goal.AttackTargetGoal;
import me.jishuna.jishlib.nms.entity.goal.DefaultGoal;
import me.jishuna.jishlib.nms.entity.goal.MeleeAttackGoal;

public class GoalHelper {

    public static void addGoal(PathfinderMob entity, int priority, DefaultGoal defaultGoal) {
        if (defaultGoal instanceof MeleeAttackGoal goal) {
            entity.goalSelector.addGoal(priority, new net.minecraft.world.entity.ai.goal.MeleeAttackGoal(entity, goal.getSpeed(), goal.shouldPauseWhenIdle()));
            return;
        }

        if (defaultGoal instanceof AttackTargetGoal goal) {
            Class<? extends Entity> targetClass = CraftEntityType.bukkitToMinecraft(goal.getType()).getBaseClass();
            System.out.println(targetClass);
            entity.targetSelector.addGoal(priority, new NearestAttackableTargetGoal(entity, targetClass, goal.isCheckVisibility()));
        }
    }
}
